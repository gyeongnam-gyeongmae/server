package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment;

import lombok.Builder;
import lombok.Data;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Comment;

import java.time.LocalDateTime;

@Builder
@Data
public class AuctionItemCommentChildDto {
    private Long id;
    private String content;
    private Long userId;
    private String nickName;
    private Integer likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long parent;

    public static AuctionItemCommentChildDto of(Comment comment) {
        return builder()
                .id(comment.getId())
                .content(comment.getContent())
                .userId(comment.getMember().getId())
                .nickName(comment.getMember().getNickname())
                .likeCount(comment.getLike_count())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .parent(comment.getParent().getId())
                .build();
    }


    public AuctionItemCommentChildDto(final Long id, final String content, final Long userId, final String nickName, final Integer likeCount, final LocalDateTime createdAt, final LocalDateTime updatedAt, final Long parent) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.nickName = nickName;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.parent = parent;
    }

    public static class AuctionItemCommentChildDtoBuilder {
        private Long id;
        private String content;
        private Long userId;
        private String nickName;
        private Integer likeCount;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Long parent;


    }
}
