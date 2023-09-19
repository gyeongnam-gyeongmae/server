package megabrain.gyeongnamgyeongmae.domain.user.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserCreateRequest {

  @NotEmpty
  @Length(min = 11, max = 11)
  private String PhoneNumber;

  @NotEmpty
  @Length(min = 6, max = 6)
  private String phoneAuthenticationCode;

  @NotEmpty
  @Pattern(
      message = "최소 한개 이상의 대소문자와 숫자, 특수문자를 포함한 8자 이상 16자 이하의 비밀번호를 입력해야 합니다.",
      regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!~$%^&-+=()])(?=\\S+$).{8,16}$")
  private String password;

  @NotEmpty private String nickname;

  public static User toEntity(
      UserCreateRequest userCreateRequest, PasswordEncoder passwordEncoder) {
    return User.builder()
        .nickname(userCreateRequest.nickname)
        .password(passwordEncoder.encode(userCreateRequest.password))
        .build();
  }
}
