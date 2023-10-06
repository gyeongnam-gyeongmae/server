package megabrain.gyeongnamgyeongmae.domain.user.dto.UserProfile;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SearchAuctionItemByUser {

  private boolean closed;

  @NotNull private Long page;
}
