package megabrain.gyeongnamgyeongmae.domain.user.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.Address;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.domain.repository.UserRepository;
import megabrain.gyeongnamgyeongmae.domain.user.exception.FailedCoordinateParse;
import megabrain.gyeongnamgyeongmae.domain.user.exception.UserNotFoundException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GeneralUserService implements UserServiceInterface {

  private final UserRepository userRepository;

  @Value("${kakao.rest-api-key}")
  private String kakaoRestApiKey;

  @Value("${kakao.transcoord-uri}")
  private String transCoordUri;

  @Override
  @Transactional
  public void registerUser(User user) {
    userRepository.save(user);
  }

  @Override
  public User findUserById(Long Id) {
    return userRepository.findById(Id).orElseThrow(UserNotFoundException::new);
  }

  @Override
  public Long getIdByAuthVendorUserId(String authVendorUserId) {
    return this.userRepository.getIdByAuthVendorUserId(authVendorUserId);
  }

  @Override
  public void withdrawUserById(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  public List<User> findAllUser() {
    return userRepository.findAll();
  }

  @Override
  @Transactional
  public void setAddress(Long id, Address address) {
    User user = userRepository.findById(id).orElseThrow(RuntimeException::new);
    user.setAddress(address);

    userRepository.save(user);
  }

  @Override
  @Transactional
  public Address getAddressByCoordinate(Float latitude, Float longitude) {

    HttpHeaders headers = new HttpHeaders();
    RestTemplate restTemplate = new RestTemplate();

    try {
      headers.add("Authorization", "KakaoAK " + kakaoRestApiKey);
      transCoordUri = transCoordUri + "?x=" + longitude + "&y=" + latitude + "&input_coord=WGS84";
      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

      ResponseEntity<String> response =
          restTemplate.exchange(transCoordUri, HttpMethod.GET, request, String.class);

      return kakaoCoordParser(response);
    } catch (Exception e) {
      throw new FailedCoordinateParse("좌표를 주소로 변환하는데 실패했습니다.");
    }
  }

  private Address kakaoCoordParser(ResponseEntity<String> response) throws Exception {
    JSONParser jsonParser = new JSONParser();
    JSONObject jsonObject = (JSONObject) jsonParser.parse(response.getBody());

    JSONObject metadata = (JSONObject) jsonObject.get("meta");
    Long size = (Long) metadata.get("total_count");
    if (size < 1) {
      throw new Exception();
    }
    JSONArray addressData = (JSONArray) jsonObject.get("documents");
    JSONObject addressInfo = (JSONObject) addressData.get(0);
    JSONObject roadAddress = (JSONObject) addressInfo.get("road_address");

    String state = (String) roadAddress.get("region_1depth_name");
    String city = (String) roadAddress.get("region_2depth_name");
    String town = (String) roadAddress.get("region_3depth_name");

    return Address.of(state, city, town);
  }
}
