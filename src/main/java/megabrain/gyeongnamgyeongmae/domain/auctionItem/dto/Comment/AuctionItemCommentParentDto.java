package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Comment;
import megabrain.gyeongnamgyeongmae.domain.image.domain.entity.Image;

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
  private String imageUrl;
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
      final String imageUrl,
      final Boolean isLiked) {
    this.id = id;
    this.content = content;
    this.userId = userId;
    this.nickName = nickName;
    this.likeCount = likeCount;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.children = children;
    this.imageUrl = imageUrl;
    this.isLiked = isLiked;
  }

  public static AuctionItemCommentParentDto of(Comment comment) {
    List<Image> images = comment.getUser().getImages().stream().filter(image -> !image.isRemoved()).collect(Collectors.toList());
    String imageUrl = "https://yt3.ggpht.com/a/default-user=s88-c-k-c0x00ffffff-no-rj";
    if (!images.isEmpty()) {
      imageUrl = images.get(0).getImageUrl();
    }
    return builder()
        .id(comment.getId())
        .content(comment.getContent())
        .userId(comment.getUser().getId())
        .nickName(comment.getUser().getNickname())
        .likeCount(comment.getLike_count())
        .createdAt(comment.getCreatedAt())
        .updatedAt(comment.getUpdatedAt())
        .imageUrl(imageUrl)
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
