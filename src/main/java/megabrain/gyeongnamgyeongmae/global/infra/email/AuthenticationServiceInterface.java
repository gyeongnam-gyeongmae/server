package megabrain.gyeongnamgyeongmae.global.infra.email;

import megabrain.gyeongnamgyeongmae.domain.authentication.domain.KakaoUserProfile;
import megabrain.gyeongnamgyeongmae.domain.member.domain.entity.Member;

public interface AuthenticationServiceInterface {

  KakaoUserProfile getKakaoUserProfile(String accessToken);

  Member getKakaoAccessTokenByKakaoCode(String code);

  String generatePhoneAuthenticationCode();

  void sendPhoneAuthenticationCode(String phoneNumber, String phoneAuthenticationCode);

  boolean isPhoneAuthenticationCodeExist(String phoneNumber, String code);
}
