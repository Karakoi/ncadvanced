package com.overseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

/**
 * The <code>PriorityStatus</code> class represents priority of request {@link Request}.
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@SuppressWarnings("PMD.UnusedPrivateField")
public class PriorityStatus extends AbstractEntity {
    @NonNull
    private String name;
}
