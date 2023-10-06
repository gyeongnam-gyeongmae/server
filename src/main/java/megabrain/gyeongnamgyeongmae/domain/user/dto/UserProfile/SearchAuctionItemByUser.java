package megabrain.gyeongnamgyeongmae.domain.user.dto.UserProfile;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SearchAuctionItemByUser {

  @NotNull private Long userId;

  @NotNull private Long page;
}
