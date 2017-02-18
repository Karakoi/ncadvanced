package com.overseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * The <code>SubRequest</code> class represents sub requests {@link Request} of office manager {@link User}.
 */
@NoArgsConstructor
@AllArgsConstructor
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
