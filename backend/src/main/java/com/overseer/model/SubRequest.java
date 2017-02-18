package com.overseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Created by Romanova on 18.02.2017.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"requestId", "previousSubRequestId"})
@ToString(callSuper = true, exclude = {"requestId", "previousSubRequestId"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
public class SubRequest extends AbstractRequest {
    @NonNull
    private Long requestId;
    private Long previousSubRequestId;
}
