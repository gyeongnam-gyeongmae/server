package megabrain.gyeongnamgyeongmae.domain.authentication.service;

import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthUserProfile;
import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthVendorName;
import megabrain.gyeongnamgyeongmae.domain.authentication.exception.OAuthLoginException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class KakaoOAuthLoginService extends OAuthLoginService {

  @Value("${kakao.profile-uri}")
  private String kakaoProfileUri;

  @Override
  public OAuthUserProfile getUserProfileByAccessToken(String token) {
    try {
      return this.getUserProfileByAccessToken(token, kakaoProfileUri);
    } catch (Exception e) {
      throw new OAuthLoginException("카카오 로그인에 실패했습니다.");
    }
  }

  @Override
  public OAuthVendorName getOAuthVendorName() {
    return OAuthVendorName.KAKAO;
  }

  @Override
  public OAuthUserProfile profileParser(ResponseEntity<String> response) throws Exception {
    OAuthUserProfile userProfile;

    try {
      JSONParser jsonParser = new JSONParser();
      JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());
      JSONObject kakaoAccount = (JSONObject) jsonObject.get("kakao_account");
      JSONObject profile = (JSONObject) kakaoAccount.get("profile");

      userProfile =
          OAuthUserProfile.builder()
              .authVendorUserId(this.getOAuthVendorName() + "_" + jsonObject.get("id"))
              .nickname(profile.get("nickname").toString())
              //              .email(kakaoAccount.get("email").toString())
              //              .profile((String) profile.get("profile_image_url"))
              .vendorIndex(getVendorIndex(this.getOAuthVendorName()))
              .build();

    } catch (ParseException e) {
      throw new OAuthLoginException("카카오 로그인에 실패했습니다.");
    }
    return userProfile;
  }
}
