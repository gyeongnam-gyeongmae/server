package megabrain.gyeongnamgyeongmae.domain.authentication.service;

import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthUserProfile;
import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthVendorName;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;

public interface AuthenticationServiceInterface {

  void login(long id);

  void logout();

  User getLoginUser();

  Long getLoginUserId();

  boolean isDuplicateAuthVendorUserId(String authVendorUserId);

  String generatePhoneAuthenticationCode();

  void sendPhoneAuthenticationCode(String phoneNumber, String phoneAuthenticationCode);

  boolean isPhoneAuthenticationCodeExist(String phoneNumber, String code);

  OAuthUserProfile oauthLoginStrategy(OAuthVendorName vendorName, String vendorAccessToken);
}
