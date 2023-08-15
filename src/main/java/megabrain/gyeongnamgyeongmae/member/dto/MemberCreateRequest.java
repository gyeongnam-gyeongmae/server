package megabrain.gyeongnamgyeongmae.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.*;
import megabrain.gyeongnamgyeongmae.member.domain.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberCreateRequest {

  @NotEmpty @Email private String email;

  @NotEmpty
  @Pattern(
      message = "최소 한개 이상의 대소문자와 숫자, 특수문자를 포함한 8자 이상 16자 이하의 비밀번호를 입력해야 합니다.",
      regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!~$%^&-+=()])(?=\\S+$).{8,16}$")
  private String password;

  @NotEmpty private String nickname;

  public static Member toEntity(
      MemberCreateRequest memberCreateRequest, PasswordEncoder passwordEncoder) {
    return Member.builder()
        .email(memberCreateRequest.email)
        .nickname(memberCreateRequest.nickname)
        .password(passwordEncoder.encode(memberCreateRequest.password))
        .build();
  }
}