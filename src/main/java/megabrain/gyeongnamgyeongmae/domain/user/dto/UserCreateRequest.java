package megabrain.gyeongnamgyeongmae.domain.user.dto;

import javax.validation.constraints.NotEmpty;
import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserCreateRequest {

  @NotEmpty
  @Length(min = 11, max = 11)
  private String PhoneNumber;

  @NotEmpty
  @Length(min = 6, max = 6)
  private String phoneAuthenticationCode;

  @NotEmpty private String nickname;

  public static User toEntity(UserCreateRequest userCreateRequest) {
    return User.builder().nickname(userCreateRequest.nickname).build();
  }
}
