package megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity;

import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.member.domain.entity.Member;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class OAuthUserProfile {
  String authVendorMemberId;
  String nickname;

  public static Member toEntity(OAuthUserProfile authUserProfile) {
    return Member.builder()
        .authVendorMemberId(authUserProfile.getAuthVendorMemberId())
        .nickname(authUserProfile.getNickname())
        .build();
  }
}
