package megabrain.gyeongnamgyeongmae.domain.user.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.Address;

@Getter
public class UserUpdateRequest {

  @NotEmpty private String phoneNumber;

  @NotEmpty private String nickname;

  @NotEmpty private String introduce;

  @NotNull private Address address;
}
