package com.overseer.event;

import com.overseer.model.ProgressStatus;
import com.overseer.model.Request;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * Custom ApplicationEvent which appears when ProgressStatus is changed.
 */
public class ChangeProgressStatusEvent extends ApplicationEvent {
    private ProgressStatus newProgressStatus;
    private Request request;
    private List<Request> joinedRequests;


    public ChangeProgressStatusEvent(Object source, ProgressStatus newProgressStatus, Request request) {
        super(source);
        this.newProgressStatus = newProgressStatus;
        this.request = request;
    }

    public ChangeProgressStatusEvent(Object source, ProgressStatus newProgressStatus, Request request, List<Request> joinedRequests) {
        super(source);
        this.newProgressStatus = newProgressStatus;
        this.request = request;
        this.joinedRequests = joinedRequests;
    }

    public ProgressStatus getNewProgressStatus() {
        return newProgressStatus;
    }

    public Request getRequest() {
        return request;
    }

    public List<Request> getJoinedRequests() {
        return joinedRequests;
    }
}
