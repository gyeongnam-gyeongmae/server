package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Comment;

@Getter
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
  private Boolean isLiked;

  public AuctionItemCommentParentDto(
      final Long id,
      final String content,
      final Long userId,
      final String nickName,
      final Integer likeCount,
      final LocalDateTime createdAt,
      final LocalDateTime updatedAt,
      final List<AuctionItemCommentChildDto> children,
      final Boolean isLiked) {
    this.id = id;
    this.content = content;
    this.userId = userId;
    this.nickName = nickName;
    this.likeCount = likeCount;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.children = children;
    this.isLiked = isLiked;
  }

  public static AuctionItemCommentParentDto of(Comment comment) {
    return builder()
        .id(comment.getId())
        .content(comment.getContent())
        .userId(comment.getUser().getId())
        .nickName(comment.getUser().getNickname())
        .likeCount(comment.getLike_count())
        .createdAt(comment.getCreatedAt())
        .updatedAt(comment.getUpdatedAt())
        .isLiked(false)
        .children(
            comment.getChildren().stream()
                .filter(childComment -> !childComment.isRemoved())
                .sorted(Comparator.comparing(Comment::getId))
                .map(AuctionItemCommentChildDto::of)
                .collect(Collectors.toList()))
        .build();
  }

  public void setIsLiked(Boolean isLiked) {
    this.isLiked = isLiked;
  }
}
