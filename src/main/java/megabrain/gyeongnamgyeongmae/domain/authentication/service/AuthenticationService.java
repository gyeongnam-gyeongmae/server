package megabrain.gyeongnamgyeongmae.domain.authentication.service;

import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthUserProfile;
import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthVendorName;
import megabrain.gyeongnamgyeongmae.domain.authentication.exception.OAuthVendorNotFoundException;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.domain.repository.UserRepository;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationServiceInterface {

  private final List<OAuthLogin> oAuthLogins;
  private final UserRepository userRepository;
  private final UserService userService;
  private final HttpSession httpSession;
  public static final String USER_ID = "USER_ID";

  @Override
  public void login(long id) {
    httpSession.setAttribute(USER_ID, id);
  }

  @Override
  public void logout() {
    httpSession.removeAttribute(USER_ID);
  }

  @Override
  public User getLoginUser() {
    Long userId = (Long) httpSession.getAttribute(USER_ID);

    return userService.findUserById(userId);
  }

  @Override
  public Long getLoginUserId() {
    return (Long) httpSession.getAttribute(USER_ID);
  }

  @Override
  public OAuthUserProfile oauthLoginStrategy(OAuthVendorName vendorName, String vendorAccessToken) {
    OAuthLogin oAuthLoginService = getOAuthLoginServiceByVendorName(vendorName);
    return oAuthLoginService.getUserProfileByAccessToken(vendorAccessToken);
  }

  @Override
  public boolean isDuplicateAuthVendorUserId(String authVendorUserId) {
    return userRepository.existsByAuthVendorUserId(authVendorUserId);
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

  private OAuthLogin getOAuthLoginServiceByVendorName(OAuthVendorName vendorName) {
    for (OAuthLogin authLoginService : oAuthLogins) {
      if (authLoginService.getOAuthVendorName().equals(vendorName)) return authLoginService;
    }

    throw new OAuthVendorNotFoundException(vendorName + "는 지원하지 않은 인증사입니다.");
  }
}
