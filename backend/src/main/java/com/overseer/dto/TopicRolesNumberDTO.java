package com.overseer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Class helper for transfer {@link com.overseer.model.Topic} id end its number of {@link com.overseer.model.Role}.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("PMD.UnusedPrivateField")
public class TopicRolesNumberDTO {
    private Long topicId;
    private Integer rolesNumber;
}
