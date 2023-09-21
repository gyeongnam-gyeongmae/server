package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuctionItemLikeRequest {

  @NotNull private Long userId;
}
