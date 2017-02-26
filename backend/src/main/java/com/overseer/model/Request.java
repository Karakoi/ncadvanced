package com.overseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * The <code>Request</code> class represents requests of users {@link User}.
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString(of = {"title", "description", "dateOfCreation", "estimateTimeInDays"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@SuppressWarnings("PMD.UnusedPrivateField")
public class Request extends AbstractEntity {
    @NonNull
    protected String title;

    protected String description;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy")
    @NonNull
    private LocalDateTime dateOfCreation;

    private PriorityStatus priorityStatus;

    private PriorityStatus progressStatus;

    @NonNull
    private User reporter;

    private User assignee;

    private Long parentId;

    private Integer estimateTimeInDays;
}
