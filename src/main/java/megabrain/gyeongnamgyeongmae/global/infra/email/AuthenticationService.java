package megabrain.gyeongnamgyeongmae.global.infra.email;

import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.authentication.domain.KakaoUserProfile;
import megabrain.gyeongnamgyeongmae.domain.member.domain.entity.Member;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationServiceInterface {

  @Override
  public KakaoUserProfile getKakaoUserProfile(String code) {
    return KakaoUserProfile.builder().nickname("nickname").email("email").id("id").build();
  }

  @Override
  public Member getKakaoAccessTokenByKakaoCode(String code) {
    return null;
  }

  @Override()
  public String generatePhoneAuthenticationCode() {
    return "123456";
  }

  @Override()
  public void sendPhoneAuthenticationCode(String phoneNumber, String phoneAuthenticationCode) {}

  @Override
  public boolean isPhoneAuthenticationCodeExist(String email, String code) {
    return true;
  }
}
