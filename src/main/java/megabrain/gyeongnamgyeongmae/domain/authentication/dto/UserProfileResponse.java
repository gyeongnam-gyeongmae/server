package megabrain.gyeongnamgyeongmae.domain.authentication.dto;

import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.Address;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserProfileResponse {
  private Long id;
  private String phoneNumber;
  private String nickname;
  private String introduce;
  private Address address;
}
