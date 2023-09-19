package megabrain.gyeongnamgyeongmae.domain.authentication.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

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

  @NotEmpty
  @Pattern(
      message = "최소 한개 이상의 대소문자와 숫자, 특수문자를 포함한 8자 이상 16자 이하의 비밀번호를 입력해야 합니다.",
      regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!~$%^&-+=()])(?=\\S+$).{8,16}$")
  private String password;

  public User toEntity(PasswordEncoder passwordEncoder, String authVendorUserId) {
    return User.builder()
        .authVendorUserId(authVendorUserId)
        .phoneNumber(phoneNumber)
        .password(passwordEncoder.encode(password))
        .build();
  }
}
