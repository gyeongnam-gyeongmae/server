package megabrain.gyeongnamgyeongmae.domain.authentication.service;

import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthUserProfile;
import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthVendorName;
import megabrain.gyeongnamgyeongmae.domain.authentication.exception.OAuthLoginException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public abstract class OAuthLoginService implements OAuthLogin {

  protected OAuthUserProfile getUserProfileByAccessToken(String accessToken, String profileUri)
      throws Exception {

    HttpHeaders headers = new HttpHeaders();
    RestTemplate restTemplate = new RestTemplate();

    if (accessToken.charAt(0) == '"') {
      accessToken = accessToken.substring(1, accessToken.length() - 1);
    }

    headers.add("Authorization", "Bearer " + accessToken);
    headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
    ResponseEntity<String> response =
        restTemplate.exchange(profileUri, HttpMethod.GET, request, String.class);

    OAuthUserProfile userProfile = this.profileParser(response);
    if (userProfile.getAuthVendorUserId() == null
        // || userProfile.getEmail() == null
        || userProfile.getNickname() == null) {
      throw new OAuthLoginException("프로필 정보를 가져오는데 실패했습니다.");
    }

    return userProfile;
  }

  static int getVendorIndex(OAuthVendorName vendorName) {
    return OAuthVendorName.valueOf(vendorName.toString()).ordinal();
  }
}
