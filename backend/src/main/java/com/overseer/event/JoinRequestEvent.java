package com.overseer.event;

import com.overseer.model.Progress;
import com.overseer.model.Request;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * Custom ApplicationEvent which appears when ProgressStatus of Request is changed into 'Joined'.
 */
public class JoinRequestEvent extends ApplicationEvent {
    private Request parentRequest;
    private List<Request> joinedRequests;
    public static final Progress PROGRESS_STATUS = Progress.JOINED;

    public JoinRequestEvent(Object source, Request parentRequest, List<Request> joinedRequests) {
        super(source);
        this.parentRequest = parentRequest;
        this.joinedRequests = joinedRequests;
    }

    public List<Request> getJoinedRequests() {
        return joinedRequests;
    }

    public Request getParentRequest() {
        return parentRequest;
    }
}
