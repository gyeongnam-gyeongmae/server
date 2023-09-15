package megabrain.gyeongnamgyeongmae.domain.authentication.service;

import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthUserProfile;
import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthVendorName;

public interface AuthenticationServiceInterface {

  OAuthUserProfile getKakaoUserProfile(String accessToken);

  void isValidateOAuthVendorName(String name);

  String generatePhoneAuthenticationCode();

  void sendPhoneAuthenticationCode(String phoneNumber, String phoneAuthenticationCode);

  boolean isPhoneAuthenticationCodeExist(String phoneNumber, String code);

  OAuthUserProfile oauthLoginStrategy(OAuthVendorName vendorName, String vendorAccessToken);

  int GetOAuthVendorIdByName(OAuthVendorName vendorName);
}
