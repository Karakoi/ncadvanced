package com.overseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

/**
 * The <code>AbstractRequest</code> class represents Abstract class of Request {@link Request}.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, exclude = {"historyDetailId"})
@ToString(exclude = {"historyDetailId"})
@SuppressWarnings("PMD.UnusedPrivateField")
public abstract class AbstractRequest extends AbstractEntity {
    @NonNull
    private String title;

    private String description;

    private Long historyDetailId;
}
