package megabrain.gyeongnamgyeongmae.domain.user.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class UserAddressCreateRequest {
  @Range(min = 0, max = 90, message = "위도는 0~90 사이의 값이어야 합니다.")
  private Float latitude;

  @Range(min = 0, max = 180, message = "경도는 0~180 사이의 값이어야 합니다.")
  private Float longitude;
}
