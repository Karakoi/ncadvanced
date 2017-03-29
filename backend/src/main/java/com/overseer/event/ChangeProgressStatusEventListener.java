package com.overseer.event;

import com.overseer.dao.RequestDao;
import com.overseer.model.PriorityStatus;

import com.overseer.model.Request;
import com.overseer.model.enums.ProgressStatus;
import com.overseer.service.EmailBuilder;
import com.overseer.service.EmailService;
import com.overseer.service.RequestSubscribeService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
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
public class ChangeProgressStatusEventListener implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    private RequestDao requestDao;

    private EmailBuilder<Request> emailStrategyForAssignee;
    private EmailBuilder<Request> emailStrategyForReporter;
    private EmailService emailService;

    @Autowired
    private RequestSubscribeService requestSubscribeService;

    public ChangeProgressStatusEventListener(RequestDao requestDao,
                                             @Qualifier("officeManagerNotificationBuilderImpl") EmailBuilder<Request> emailStrategyForAssignee,
                                             @Qualifier("employeeNotificationBuilderImpl") EmailBuilder<Request> emailStrategyForReporter,
                                             EmailService emailService) {
        this.requestDao = requestDao;
        this.emailStrategyForAssignee = emailStrategyForAssignee;
        this.emailStrategyForReporter = emailStrategyForReporter;
        this.emailService = emailService;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    /**
     * Assigns request to specified office manager and changes it {@link Request#progressStatus}.
     *
     * @param assignRequestEvent event of changing progress status
     */
    @EventListener
    public void assignRequest(AssignRequestEvent assignRequestEvent) {
        Request request = assignRequestEvent.getRequest();
        changeStatusAndSave(request, AssignRequestEvent.PROGRESS_STATUS);
    }

    /**
     * Sending message to all subscribers of email when request progress has changed.
     *
     * @param changeProgressStatusEvent event of changing progress status.
     */
    @EventListener(condition = "#changeProgressStatusEvent.newProgressStatus != null ")
    public void testEvent(ChangeProgressStatusEvent changeProgressStatusEvent) {
        val subscribers = requestSubscribeService.getSubscribersOfRequest(changeProgressStatusEvent.getRequest().getId());
        SimpleMailMessage message = emailStrategyForAssignee.buildMessage(changeProgressStatusEvent.getRequest());
        if (!subscribers.isEmpty()) {
            val subscribersEmail  = subscribers.stream().map(User::getEmail).toArray(String[]::new);
            message.setTo(subscribersEmail);
            emailService.sendMessage(message);
        }
    }

    /**
     * Closes request and changes it {@link Request#progressStatus}.
     *
     * @param closeRequestEvent event of changing progress status
     */
    @EventListener
    public void closeRequest(CloseRequestEvent closeRequestEvent) {
        Request request = closeRequestEvent.getRequest();
        //Check if request is parent
        List<Request> joinedRequests = requestDao.findJoinedRequests(request);
        if (joinedRequests.isEmpty()) {
            Long parentRequestId = request.getParentId();
            request.setParentId(null);
            changeStatusAndSave(request, CloseRequestEvent.PROGRESS_STATUS);
            if (parentRequestId != null) {
                requestDao.deleteParentRequestIfItHasNoChildren(parentRequestId);
            }
        } else {
            for (Request joinedRequest: joinedRequests) {
                joinedRequest.setParentId(null);
                changeStatusAndSave(joinedRequest, CloseRequestEvent.PROGRESS_STATUS);
            }
            List<Request> subRequests = requestDao.findSubRequests(request);
            subRequests.forEach(requestDao::delete);
            requestDao.deleteParentRequestIfItHasNoChildren(request.getParentId());
        }
    }

    /**
     * Reopens request and changes it {@link Request#progressStatus}.
     *
     * @param reopenRequestEvent event of changing progress status
     */
    @EventListener
    public void reopenRequest(ReopenRequestEvent reopenRequestEvent) {
        Request request = reopenRequestEvent.getRequest();
        request.setEstimateTimeInDays(null);

        changeStatusAndSave(request, ReopenRequestEvent.PROGRESS_STATUS);

        sendMessageToAssignee(request);
        request.getAssignee().setId(null);
        requestDao.save(request);
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
     *
     * @param request        specified request
     * @param progressStatus specified progressStatus
     */
    private void changeStatusAndSave(Request request, ProgressStatus progressStatus) {
        request.setProgressStatus(progressStatus);
        requestDao.save(request);

        sendMessageToReporter(request);

        RequestChangeEvent event = new RequestChangeEvent(this, request);
        publisher.publishEvent(event);
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
        SimpleMailMessage message = this.emailStrategyForAssignee.buildMessage(request);
        emailService.sendMessage(message);
    }

    /**
     * Sends notification to Reporter of request.
     *
     * @param request request with changed {@link ProgressStatus}
     */
    private void sendMessageToReporter(Request request) {
        SimpleMailMessage message = this.emailStrategyForReporter.buildMessage(request);
        emailService.sendMessage(message);
    }
}
