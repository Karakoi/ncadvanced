package com.overseer.event;

import com.overseer.dao.RequestDao;
import com.overseer.model.PriorityStatus;
import com.overseer.model.Request;
import com.overseer.model.enums.ProgressStatus;
import com.overseer.service.EmailBuilder;
import com.overseer.service.EmailService;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private RequestDao requestDao;
    private EmailBuilder<Request> emailStrategyForAssignee;
    private EmailBuilder<Request> emailStrategyForReporter;
    private EmailService emailService;

    public ChangeProgressStatusEventListener(RequestDao requestDao,
                                             @Qualifier("officeManagerNotificationBuilderImpl") EmailBuilder<Request> emailStrategyForAssignee,
                                             @Qualifier("employeeNotificationBuilderImpl") EmailBuilder<Request> emailStrategyForReporter,
                                             EmailService emailService) {
        this.requestDao = requestDao;
        this.emailStrategyForAssignee = emailStrategyForAssignee;
        this.emailStrategyForReporter = emailStrategyForReporter;
        this.emailService = emailService;
    }

    /**
     * Assigns request to specified office manager and changes it {@link Request#progressStatus}.
     *
     * @param changeProgressStatusEvent event of changing progress status
     */
    @EventListener(condition = "#changeProgressStatusEvent.newProgressStatus.name == 'IN_PROGRESS'")
    public void assignRequest(ChangeProgressStatusEvent changeProgressStatusEvent) {
        Request request = changeProgressStatusEvent.getRequest();
        ProgressStatus progressStatus = changeProgressStatusEvent.getNewProgressStatus();
        changeStatusAndSave(request, progressStatus);
    }

    /**
     * Closes request and changes it {@link Request#progressStatus}.
     *
     * @param changeProgressStatusEvent event of changing progress status
     */
    @EventListener(condition = "#changeProgressStatusEvent.newProgressStatus.name == 'CLOSED'")
    public void closeRequest(ChangeProgressStatusEvent changeProgressStatusEvent) {
        Request request = changeProgressStatusEvent.getRequest();
        ProgressStatus closedProgressStatus = changeProgressStatusEvent.getNewProgressStatus();
        //Check if request is parent
        List<Request> joinedRequests = requestDao.findJoinedRequests(request);
        if (joinedRequests.isEmpty()) {
            Long parentRequestId = request.getParentId();
            request.setParentId(null);
            changeStatusAndSave(request, closedProgressStatus);
            if (parentRequestId != null) {
                requestDao.deleteParentRequestIfItHasNoChildren(parentRequestId);
            }
        } else {
            for (Request joinedRequest : joinedRequests) {
                joinedRequest.setParentId(null);
                changeStatusAndSave(joinedRequest, closedProgressStatus);
            }
            List<Request> subRequests = requestDao.findSubRequests(request);
            subRequests.forEach(requestDao::delete);
            requestDao.deleteParentRequestIfItHasNoChildren(request.getParentId());
        }
    }

    /**
     * Reopens request and changes it {@link Request#progressStatus}.
     *
     * @param changeProgressStatusEvent event of changing progress status
     */
    @EventListener(condition = "#changeProgressStatusEvent.newProgressStatus.name == 'FREE'")
    public void reopenRequest(ChangeProgressStatusEvent changeProgressStatusEvent) {
        Request request = changeProgressStatusEvent.getRequest();
        ProgressStatus freeProgressStatus = changeProgressStatusEvent.getNewProgressStatus();

        request.setEstimateTimeInDays(null);

        changeStatusAndSave(request, freeProgressStatus);

        sendMessageToAssignee(request);
        request.setAssignee(null);
        requestDao.save(request);
    }

    /**
     * Joins specified requests by parent request.
     * Joined requests will have 'Joined' {@link Request#progressStatus}
     * and not null {@link Request#parentId}.
     *
     * @param changeProgressStatusEvent event of changing progress status
     */
    @EventListener(condition = "#changeProgressStatusEvent.newProgressStatus.name == 'JOINED'")
    public void joinRequestsIntoParent(ChangeProgressStatusEvent changeProgressStatusEvent) {
        Request parentRequest = changeProgressStatusEvent.getRequest();
        ProgressStatus joinedProgressStatus = changeProgressStatusEvent.getNewProgressStatus();
        List<Request> joinedRequests = changeProgressStatusEvent.getJoinedRequests();

        // Find and set max priority status from specified requests to parent request
        PriorityStatus maxPriorityStatus = getMaxPriorityStatus(joinedRequests);
        parentRequest.setPriorityStatus(maxPriorityStatus);
        parentRequest.setDateOfCreation(LocalDateTime.now());
        // Save parent request to database
        Request parent = requestDao.save(parentRequest);

        // Update child requests with new progress status and parent id
        Long parentId = parent.getId();

        joinedRequests.forEach(request -> {
            request.setParentId(parentId);
            request.setAssignee(parentRequest.getAssignee());
            request.setEstimateTimeInDays(parentRequest.getEstimateTimeInDays());
            request.setLastChanger(parentRequest.getAssignee());
            changeStatusAndSave(request, joinedProgressStatus);
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
