package megabrain.gyeongnamgyeongmae.domain.authentication.service;

import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthUserProfile;
import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthVendorName;
import megabrain.gyeongnamgyeongmae.domain.member.domain.entity.Member;

public interface AuthenticationServiceInterface {

  boolean isDuplicateAuthVendorMemberId(String authVendorMemberId);

  void saveMember(Member member);

  OAuthUserProfile getKakaoUserProfile(String accessToken);

  void isValidateOAuthVendorName(String name);

  String generatePhoneAuthenticationCode();

  void sendPhoneAuthenticationCode(String phoneNumber, String phoneAuthenticationCode);

  boolean isPhoneAuthenticationCodeExist(String phoneNumber, String code);

  OAuthUserProfile oauthLoginStrategy(OAuthVendorName vendorName, String vendorAccessToken);

  int GetOAuthVendorIdByName(OAuthVendorName vendorName);
}
