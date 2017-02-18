package com.overseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

/**
 * Created by Romanova on 18.02.2017.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, exclude = {"historyDetail"})
@ToString(callSuper = false, exclude = {"historyDetail"})
public abstract class AbstractRequest extends AbstractEntity {
    @NonNull
    private String title;
    @NonNull
    private String description;
    @NonNull
    private Long historyDetailId;
}
