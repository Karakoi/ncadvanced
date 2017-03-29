package com.overseer.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



/**
 * The <code>Request</code> class represents requests of users {@link User}.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@SuppressWarnings("PMD.UnusedPrivateField")
public class Request extends AbstractEntity {
    private static final int MIN_TITLE_LENGTH = 5;
    private static final int MAX_TITLE_LENGTH = 45;
    private static final int MIN_DESCRIPTION_LENGTH = 10;
    private static final int MAX_DESCRIPTION_LENGTH = 500;

    @NotNull
    @Size(min = MIN_TITLE_LENGTH, max = MAX_TITLE_LENGTH)
    private String title;

    @NotNull
    @Size(min = MIN_DESCRIPTION_LENGTH, max = MAX_DESCRIPTION_LENGTH)
    private String description;

    @NotNull
    private LocalDateTime dateOfCreation;

    @JsonInclude(NON_NULL)
    private PriorityStatus priorityStatus;

    @JsonInclude(NON_NULL)
    private ProgressStatus progressStatus;

    @NotNull
    @JsonInclude(NON_NULL)
    private User reporter;

    @JsonInclude(NON_NULL)
    private User assignee;

    @JsonInclude(NON_NULL)
    private Long parentId;

    @NotNull
    @JsonInclude(NON_NULL)
    private User lastChanger;

    private Integer estimateTimeInDays;
}
