package megabrain.gyeongnamgyeongmae.domain.authentication.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class KakaoUserProfile {
  String id;
  String nickname;
  String email;

  public KakaoUserProfile(String id, String nickname, String email) {
    this.id = id;
    this.nickname = nickname;
    this.email = email;
  }
}
