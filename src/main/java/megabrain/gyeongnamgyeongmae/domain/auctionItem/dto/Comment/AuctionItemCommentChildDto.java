package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Getter;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Comment;
import megabrain.gyeongnamgyeongmae.domain.image.domain.entity.Image;

@Builder
@Getter
public class AuctionItemCommentChildDto {
  private Long id;
  private String content;
  private Long userId;
  private String nickName;
  private Integer likeCount;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Long parent;
  private String imageUrl;

  public static AuctionItemCommentChildDto of(Comment comment) {
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
        .parent(comment.getParent().getId())
        .imageUrl(imageUrl)
        .build();
  }
//
//  public AuctionItemCommentChildDto(
//      final Long id,
//      final String content,
//      final Long userId,
//      final String nickName,
//      final Integer likeCount,
//      final LocalDateTime createdAt,
//      final LocalDateTime updatedAt,
//      final Long parent) {
//    this.id = id;
//    this.content = content;
//    this.userId = userId;
//    this.nickName = nickName;
//    this.likeCount = likeCount;
//    this.createdAt = createdAt;
//    this.updatedAt = updatedAt;
//    this.parent = parent;
//  }
//
}
