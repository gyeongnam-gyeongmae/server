package megabrain.gyeongnamgyeongmae.domain.authentication.service;

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
  public OAuthUserProfile oauthLoginStrategy(String vendorName, String vendorAccessToken) {
    if (OAuthVendorName.KAKAO.toString().equals(vendorName)) {
      return this.getKakaoUserProfile(vendorAccessToken);
    }
    throw new OAuthVendorNotFoundException(vendorName + "는 지원하지 않은 인증사입니다.");
  }

  @Override
  public int GetOAuthVendorIdByName(String vendorName) {
    int vendorId = 1;
    for (OAuthVendorName oAuthVendorName : OAuthVendorName.values()) {
      if (oAuthVendorName.toString().equals(vendorName)) {
        return vendorId;
      }
      vendorId++;
    }
    return 0;
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
