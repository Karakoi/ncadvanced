package com.overseer.model;

import lombok.*;

/**
 * JoinedRequest entity.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("PMD.UnusedPrivateField")
public class JoinedRequest extends AbstractRequest {

    @NonNull
    private Long assigneeId;

    private Long previousJoinedRequestId;

    @NonNull
    private Long priorityStatusId;

    @NonNull
    private Long progressStatusId;

}
