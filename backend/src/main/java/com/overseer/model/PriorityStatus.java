package com.overseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * The <code>PriorityStatus</code> class represents priority of request {@link Request}.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@SuppressWarnings("PMD.UnusedPrivateField")
public class PriorityStatus extends AbstractEntity {

    @NotNull(message = "Role has to have name")
    private String name;

    @NotNull(message = "Role has to have value")
    private int value;
}
