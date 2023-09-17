package megabrain.gyeongnamgyeongmae.domain.authentication.service;

import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthUserProfile;
import megabrain.gyeongnamgyeongmae.domain.member.domain.entity.Member;

public interface AuthenticationServiceInterface {

  boolean isDuplicateAuthVendorMemberId(String authVendorMemberId);

  void saveMember(Member member);

  OAuthUserProfile getKakaoUserProfile(String accessToken);

  String generatePhoneAuthenticationCode();

  void sendPhoneAuthenticationCode(String phoneNumber, String phoneAuthenticationCode);

  boolean isPhoneAuthenticationCodeExist(String phoneNumber, String code);

  OAuthUserProfile oauthLoginStrategy(String vendorName, String vendorAccessToken);

  int GetOAuthVendorIdByName(String vendorName);
}
