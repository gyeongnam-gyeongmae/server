package megabrain.gyeongnamgyeongmae.domain.authentication.dto;

import javax.validation.constraints.NotEmpty;
import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserRegisterRequest {

  @NotEmpty private String vendorAccessToken;

  @NotEmpty
  @Length(min = 11, max = 11, message = "휴대전화번호는 11자리입니다.")
  private String phoneNumber;

  @NotEmpty
  @Length(min = 6, max = 6, message = "인증번호는 6자리입니다.")
  private String phoneAuthenticationCode;

  public User toEntity(String authVendorUserId) {
    return User.builder().authVendorUserId(authVendorUserId).phoneNumber(phoneNumber).build();
  }
}
