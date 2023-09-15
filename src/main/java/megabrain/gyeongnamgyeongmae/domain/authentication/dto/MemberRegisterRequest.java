package megabrain.gyeongnamgyeongmae.domain.authentication.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.member.domain.entity.Member;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberRegisterRequest {

  @NotEmpty private String vendorAccessToken;

  @NotEmpty
  @Length(min = 11, max = 11)
  private String phoneNumber;

  @NotEmpty
  @Length(min = 6, max = 6)
  private String phoneAuthenticationCode;

  @NotEmpty
  @Pattern(
      message = "최소 한개 이상의 대소문자와 숫자, 특수문자를 포함한 8자 이상 16자 이하의 비밀번호를 입력해야 합니다.",
      regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!~$%^&-+=()])(?=\\S+$).{8,16}$")
  private String password;

  @NotEmpty private String nickname;

  public static Member toEntity(
      MemberRegisterRequest memberRegisterRequest,
      PasswordEncoder passwordEncoder,
      int authVendor,
      String authVendorMemberId) {

    return Member.builder()
        .phoneNumber(memberRegisterRequest.getPhoneNumber())
        .nickname(memberRegisterRequest.getNickname())
        .authVendor(authVendor)
        .authVendorMemberId(authVendorMemberId)
        .password(passwordEncoder.encode(memberRegisterRequest.getPassword()))
        .build();
  }
}
