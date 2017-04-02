package com.overseer.event;

import com.overseer.dao.RequestDao;
import com.overseer.exception.InappropriateProgressStatusException;
import com.overseer.model.PriorityStatus;

import com.overseer.model.Request;
import com.overseer.model.User;
import com.overseer.model.enums.ProgressStatus;
import com.overseer.service.EmailService;
import com.overseer.service.RequestSubscribeService;
import com.overseer.service.impl.email.NotificationMessageBuilder;
import com.overseer.service.impl.email.UniversalMessageBuilder;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * Listener for ChangeProgressStatusEvent.
 */
@Component
public class ChangeProgressStatusEventListener {
    @Autowired
    private RequestDao requestDao;

    @Autowired
    private EmailService emailService;
    @Autowired
    private NotificationMessageBuilder notificationMessageBuilder;

    @Autowired
    private RequestSubscribeService requestSubscribeService;
    @Autowired
    private UniversalMessageBuilder universalMessageBuilder;

    /**
     * Assigns request to specified office manager and changes it {@link Request#progressStatus}.
     *
     * @param changeProgressEvent event of changing progress status
     */
    @EventListener(condition = "#changeProgressEvent.request.progressStatus.getName() == 'Free' "
            + "&& #changeProgressEvent.progressStatus.getName() == 'In progress'")
    public void assignRequest(ChangeProgressEvent changeProgressEvent) {
        Request request = changeProgressEvent.getRequest();
        changeStatusAndSave(request, changeProgressEvent.getProgressStatus());
        changeProgressEvent.setIsHandled(true);
    }

    /**
     * Closes request and changes it {@link Request#progressStatus}.
     *
     * @param changeProgressEvent event of changing progress status
     */
    @EventListener(condition = "(#changeProgressEvent.request.progressStatus.getName() == 'In progress' "
            + "|| #changeProgressEvent.request.progressStatus.getName() == 'Joined') "
            + "&& #changeProgressEvent.progressStatus.getName() == 'Closed'")
    public void closeRequest(ChangeProgressEvent changeProgressEvent) {
        Request request = changeProgressEvent.getRequest();
        //Check if request is parent
        List<Request> joinedRequests = requestDao.findJoinedRequests(request);
        //if request is not parent
        if (joinedRequests.isEmpty()) {
            Long parentRequestId = request.getParentId();
            request.setParentId(null);
            changeStatusAndSave(request, changeProgressEvent.getProgressStatus());
            //if request is joined
            if (parentRequestId != null) {
                requestDao.deleteParentRequestIfItHasNoChildren(parentRequestId);
            }
        } else {
            for (Request joinedRequest : joinedRequests) {
                joinedRequest.setParentId(null);
                changeStatusAndSave(joinedRequest, changeProgressEvent.getProgressStatus());
            }
            List<Request> subRequests = requestDao.findSubRequests(request);
            subRequests.forEach(requestDao::delete);
            requestDao.deleteParentRequestIfItHasNoChildren(request.getId());
        }
        changeProgressEvent.setIsHandled(true);
    }

    /**
     * Reopens request and changes it {@link Request#progressStatus}.
     *
     * @param changeProgressEvent event of changing progress status
     */
    @EventListener(condition = "#changeProgressEvent.request.progressStatus.getName() == 'Closed' "
            + "&& #changeProgressEvent.progressStatus.getName() == 'Free'")
    public void reopenRequest(ChangeProgressEvent changeProgressEvent) {
        Request request = changeProgressEvent.getRequest();
        request.setEstimateTimeInDays(null);

        changeStatusAndSave(request, changeProgressEvent.getProgressStatus());

        sendMessageToAssignee(request);
        request.getAssignee().setId(null);
        requestDao.save(request);
        changeProgressEvent.setIsHandled(true);
    }

    /**
     * Joins specified requests by parent request.
     * Joined requests will have 'Joined' {@link Request#progressStatus}
     * and not null {@link Request#parentId}.
     *
     * @param joinRequestEvent event of changing progress status
     */
    @EventListener
    public void joinRequestsIntoParent(JoinRequestEvent joinRequestEvent) {
        Request parentRequest = joinRequestEvent.getParentRequest();
        List<Request> joinedRequests = joinRequestEvent.getJoinedRequests();

        //check if joinedRequests are appropriate
        joinedRequests.forEach(request -> {
            if (!ProgressStatus.FREE.getId().equals(request.getProgressStatus().getId())) {
                throw new InappropriateProgressStatusException("Can not join request with id: " + request.getId()
                        + " because it has progress status that is not [Free]");
            }
        });

        // Find and set max priority status from specified requests to parent request
        PriorityStatus maxPriorityStatus = getMaxPriorityStatus(joinedRequests);
        parentRequest.setPriorityStatus(maxPriorityStatus);
        // Set progress status
        parentRequest.setProgressStatus(ProgressStatus.IN_PROGRESS);
        parentRequest.setDateOfCreation(LocalDateTime.now());
        // Save parent request to database
        Request parent = requestDao.save(parentRequest);

        // Update child requests with new progress status and parent id
        Long parentId = parent.getId();

        joinedRequests.forEach(request -> {
            request.setParentId(parentId);
            request.setAssignee(parentRequest.getAssignee());
            request.setEstimateTimeInDays(parentRequest.getEstimateTimeInDays());
            changeStatusAndSave(request, JoinRequestEvent.PROGRESS_STATUS);
        });
    }

    /**
     * Changes progress status, save request and send message to Reporter.
     * and send message to all subscribers of email when request progress has changed.
     *
     * @param request        specified request
     * @param progressStatus specified progressStatus
     */
    private void changeStatusAndSave(Request request, ProgressStatus progressStatus) {
        request.setProgressStatus(progressStatus);
        Request savedRequest = requestDao.save(request);
        sendMessageToReporter(request);
        List<User> subscribers = requestSubscribeService.getSubscribersOfRequest(savedRequest.getId());
        sendMessageToSubscribers(savedRequest, subscribers);
    }

    /**
     * Returns max {@link PriorityStatus} of specified requests list.
     * Statuses compares by {@link PriorityStatus#value}.
     *
     * @param requests specified requests list
     * @return max priority status
     */
    private PriorityStatus getMaxPriorityStatus(List<Request> requests) {
        return requests
                .stream()
                .map(Request::getPriorityStatus)
                .max(Comparator.comparingInt(PriorityStatus::getValue))
                .orElseThrow(UnsupportedOperationException::new);
    }

    /**
     * Sends notification to Assignee of request.
     *
     * @param request request with changed {@link ProgressStatus}
     */
    private void sendMessageToAssignee(Request request) {
        SimpleMailMessage message = notificationMessageBuilder.getMessageBody(request, request.getAssignee());
        emailService.sendMessage(message);
    }

    /**
     * Sends notification to Reporter of request.
     *
     * @param request request with changed {@link ProgressStatus}
     */
    private void sendMessageToReporter(Request request) {
        SimpleMailMessage message =  notificationMessageBuilder.getMessageBody(request, request.getReporter());
        emailService.sendMessage(message);
    }

    /**
     * Sends notification to Reporter of request.
     *
     * @param request request with changed {@link ProgressStatus}
     * @param subscribers {@link User} who subscribed to notification
     */
    private void sendMessageToSubscribers(Request request, List<User> subscribers) {
        for (User sub : subscribers) {
            val message = universalMessageBuilder.getMessageBody(request, sub);
            emailService.sendMessage(message);
        }
    }
}
