package com.overseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

/**
 * The <code>PriorityStatus</code> class represents priority of request {@link Request}.
 */
@SuppressWarnings("PMD.UnusedPrivateField")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
public class PriorityStatus extends AbstractEntity {
    @NonNull
    private String name;
}
