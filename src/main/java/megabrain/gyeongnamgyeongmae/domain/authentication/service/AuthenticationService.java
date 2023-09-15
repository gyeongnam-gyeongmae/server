package megabrain.gyeongnamgyeongmae.domain.authentication.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthUserProfile;
import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthVendorName;
import megabrain.gyeongnamgyeongmae.domain.authentication.exception.OAuthVendorNotFoundException;
import megabrain.gyeongnamgyeongmae.domain.member.domain.entity.Member;
import megabrain.gyeongnamgyeongmae.domain.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationServiceInterface {

  private final MemberRepository memberRepository;

  @Override
  public OAuthUserProfile oauthLoginStrategy(OAuthVendorName vendorName, String vendorAccessToken) {
    if (Objects.requireNonNull(vendorName) == OAuthVendorName.KAKAO) {
      return this.getKakaoUserProfile(vendorAccessToken);
    }
    throw new OAuthVendorNotFoundException("지원하지 않은 인증사입니다.");
  }

  @Override
  public int GetOAuthVendorIdByName(OAuthVendorName vendorName) {
    switch (vendorName) {
      case KAKAO:
        return 1;
      default:
        return 0;
    }
  }

  @Override
  public boolean isDuplicateAuthVendorMemberId(String authVendorMemberId) {
    // return memberRepository.existsByAuthVendorMemberId(authVendorMemberId);
    return false;
  }

  public void saveMember(Member member) {
    memberRepository.save(member);
  }

  @Override
  public OAuthUserProfile getKakaoUserProfile(String code) {

    return OAuthUserProfile.builder().authVendorMemberId("1234").nickname("test").build();
  }

  @Override
  public void isValidateOAuthVendorName(String name) {
    throw new OAuthVendorNotFoundException("지원하지 않은 인증사입니다.");
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
