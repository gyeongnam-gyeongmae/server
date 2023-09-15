package megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class OAuthUserProfile {
  String authVendorMemberId;
  String nickname;

  public OAuthUserProfile(String authVendorMemberId, String nickname) {
    this.authVendorMemberId = authVendorMemberId;
    this.nickname = nickname;
  }
}
