package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuctionItemCommentDeleteRequest {

  @NotNull private Long userId;
  @NotNull private Long commentId;
}
