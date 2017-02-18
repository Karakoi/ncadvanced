package com.overseer.model;

import lombok.*;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class JoinedRequest extends AbstractEntity {

    @NonNull
    private String title;

    private String description;

    @NonNull
    private Long assigneeId;

    private Long historyDetailId;

    private Long previousJoinedRequestId;

    @NonNull
    private Long priorityStatusId;

    @NonNull
    private Long progressStatusId;

}
