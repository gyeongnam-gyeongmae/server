package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.global.BaseTimeEntity;

@Getter
@Entity
@Table(name = "comment_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike extends BaseTimeEntity {
  @EmbeddedId private CommentLikePK id;

  @MapsId("comment_id")
  @ManyToOne
  @JoinColumn(name = "comment_id")
  private Comment comment;

  @MapsId("user_id")
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Builder
  public CommentLike(CommentLikePK id, Comment comment, User user) {
    this.id = id;
    this.comment = comment;
    this.user = user;
  }
}
