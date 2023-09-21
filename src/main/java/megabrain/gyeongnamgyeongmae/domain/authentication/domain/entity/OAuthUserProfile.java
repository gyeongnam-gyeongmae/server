package megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity;

import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class OAuthUserProfile {
  private String authVendorUserId;
  private String nickname;
  //  private String profile;
  //  private String email;
  private int vendorIndex;

  public User toUserEntity() {
    return User.builder().authVendorUserId(authVendorUserId).nickname(nickname).build();
  }
}
