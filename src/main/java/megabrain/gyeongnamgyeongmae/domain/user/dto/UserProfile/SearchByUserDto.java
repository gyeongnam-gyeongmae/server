package megabrain.gyeongnamgyeongmae.domain.user.dto.UserProfile;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SearchByUserDto {

  //최신순, 오래된순
  private boolean closed;

  @NotNull private Long page;

}
