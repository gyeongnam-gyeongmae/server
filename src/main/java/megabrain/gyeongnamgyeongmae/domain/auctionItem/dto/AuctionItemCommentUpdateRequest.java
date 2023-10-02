package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class AuctionItemCommentUpdateRequest {
  private @NotEmpty String content;
  private @NotEmpty Long userId;
  private @NotEmpty Long commentId;
}
