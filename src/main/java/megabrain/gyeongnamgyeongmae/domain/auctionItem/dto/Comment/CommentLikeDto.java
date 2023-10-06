package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CommentLikeDto {
  @NotNull private Long userId;

  @NotNull private Long commentId;
}
