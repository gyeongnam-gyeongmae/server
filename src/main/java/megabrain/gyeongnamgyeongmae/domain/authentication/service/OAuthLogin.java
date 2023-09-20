package megabrain.gyeongnamgyeongmae.domain.authentication.service;

import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthUserProfile;
import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthVendorName;
import org.springframework.http.ResponseEntity;

public interface OAuthLogin {
  OAuthUserProfile getUserProfileByAccessToken(String token);

  OAuthVendorName getOAuthVendorName();

  OAuthUserProfile profileParser(ResponseEntity<String> response) throws Exception;
}
