package com.overseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * The <code>ProgressStatus</code> class represents progress status of request {@link Request}.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@SuppressWarnings("PMD.UnusedPrivateField")
public class ProgressStatus extends AbstractEntity {

    @NotNull(message = "ProgressStatus has to have name")
    private String name;

    @NotNull(message = "ProgressStatus has to have name")
    private int value;
}
