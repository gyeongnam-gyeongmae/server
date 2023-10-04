package megabrain.gyeongnamgyeongmae.domain.user.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.Address;
import reactor.util.annotation.Nullable;

@Getter
public class UserUpdateRequest {

  @NotEmpty @Nullable private String phoneNumber;

  @NotEmpty @Nullable private String nickname;

  @NotEmpty @Nullable private String introduce;

  @Nullable private Address address;
}
