package megabrain.gyeongnamgyeongmae.domain.authentication.dto;

import javax.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserLoginRequest {

  @NotEmpty private String vendorAccessToken;
}
