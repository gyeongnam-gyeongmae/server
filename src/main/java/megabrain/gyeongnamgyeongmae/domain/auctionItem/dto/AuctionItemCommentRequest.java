package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Comment;

@Getter
public class AuctionItemCommentRequest {

  private @NotEmpty String content;
  private @NotEmpty Long userId;
  private @NotEmpty Long parentCommentId;

  public Comment toEntity() {
    return Comment.builder().content(this.content).build();
  }
}
