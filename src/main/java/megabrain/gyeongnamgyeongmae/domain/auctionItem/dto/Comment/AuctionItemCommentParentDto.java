package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment;


import lombok.Builder;
import lombok.Data;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class AuctionItemCommentParentDto {
    private Long id;
    private String content;
    private Long userId;
    private String nickName;
    private Integer likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<AuctionItemCommentChildDto> children;

    public static AuctionItemCommentParentDto of(Comment comment) {
        return builder()
                .id(comment.getId())
                .content(comment.getContent())
                .userId(comment.getMember().getId())
                .nickName(comment.getMember().getNickname())
                .likeCount(comment.getLike_count())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .children(comment.getChildren().stream().map(AuctionItemCommentChildDto::of).collect(Collectors.toList()))
                .build();
    }

    public AuctionItemCommentParentDto(final Long id, final String content, final Long userId, final String nickName, final Integer likeCount, final LocalDateTime createdAt, final LocalDateTime updatedAt, final List<AuctionItemCommentChildDto> children) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.nickName = nickName;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.children = children;
    }
}

