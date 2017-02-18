package com.overseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Romanova on 18.02.2017.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"priorityStatus", "progressStatus", "subRequestIds"})
@ToString(callSuper = true, exclude = {"priorityStatus", "progressStatus", "reporterId", "assigneeId", "joinedRequestId", "previousRequestId", "subRequestIds"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
public class Request extends AbstractRequest{
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss")
    @NonNull
    private Date dateOfCreation;
    @NonNull
    private PriorityStatus priorityStatus;
    @NonNull
    private ProgressStatus progressStatus;
    @NonNull
    private Long reporterId;
    private Long assigneeId;
    private Integer estimateTimeInDays;
    private Long joinedRequestId;
    private Long previousRequestId;

    private List<Long> subRequestIds;
}
