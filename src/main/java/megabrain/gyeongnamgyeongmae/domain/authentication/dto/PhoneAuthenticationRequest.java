package megabrain.gyeongnamgyeongmae.domain.authentication.dto;

import javax.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PhoneAuthenticationRequest {
  @NotEmpty private String phoneNumber;
}
