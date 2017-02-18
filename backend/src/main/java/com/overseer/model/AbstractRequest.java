package com.overseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

/**
 * The <code>AbstractRequest</code> class represents Abstract class of Request {@link Request}.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, exclude = {"historyDetailId"})
@ToString(callSuper = false, exclude = {"historyDetailId"})
public abstract class AbstractRequest extends AbstractEntity {
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private Long historyDetailId;
}
