package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Comment;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentFirstView {

    private Long commentId;

    private Long auctionItemId;

    private String commentContent;

    private LocalDateTime commentCreatedAt;

    private LocalDateTime commentUpdatedAt;

    private Long commentLikeCount;

    public CommentFirstView(Comment comment) {
        this.commentId = comment.getId();
        this.auctionItemId = comment.getAuctionItem().getId();
        this.commentContent = comment.getContent();
        this.commentCreatedAt = comment.getCreatedAt();
        this.commentUpdatedAt = comment.getUpdatedAt();
        this.commentLikeCount = Long.valueOf(comment.getLike_count());
    }

}
