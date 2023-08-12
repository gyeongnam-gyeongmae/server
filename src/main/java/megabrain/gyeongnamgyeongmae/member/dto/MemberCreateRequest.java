package megabrain.gyeongnamgyeongmae.member.dto;

import lombok.*;
import megabrain.gyeongnamgyeongmae.member.domain.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class MemberCreateRequest {

  private String email;

  private String password;

  private String nickname;

  public static Member toEntity(MemberCreateRequest memberCreateRequest, PasswordEncoder passwordEncoder) {
    return Member.builder()
        .email(memberCreateRequest.email)
        .nickname(memberCreateRequest.nickname)
        .password(passwordEncoder.encode(memberCreateRequest.password)).build();
  }
}

