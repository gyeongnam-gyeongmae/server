package megabrain.gyeongnamgyeongmae.domain.authentication.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MailAuthenticationRequest {
  @NotEmpty @Email private String email;
}
