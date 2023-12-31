package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.global.BaseTimeEntity;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Long id;

  @Column(nullable = false, length = 800)
  private String content;

  @Column(name = "removed")
  private boolean removed = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "auction_id")
  private AuctionItem auctionItem;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Comment parent;

  @OneToMany(mappedBy = "parent", orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Comment> children = new ArrayList<>();

  @Column(name = "like_count")
  private Integer like_count = 0;

  public Comment(String content, AuctionItem auctionItem, User user, Comment parent) {
    this.content = content;
    this.auctionItem = auctionItem;
    this.user = user;
    this.parent = parent;
  }

  @Builder
  public Comment(String content, User user, Comment parent, List<Comment> children) {
    this.content = content;
    this.user = user;
    this.parent = parent;
    this.children = children;
  }

  @Builder
  public Comment(
      String content,
      AuctionItem auctionItem,
      User user,
      Comment parent,
      List<Comment> children,
      Integer like_count) {
    this.content = content;
    this.auctionItem = auctionItem;
    this.user = user;
    this.parent = parent;
    this.children = children;
    this.like_count = like_count;
  }

  public void setAuctionItem(AuctionItem auctionItem) {
    this.auctionItem = auctionItem;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setParent(Comment parent) {
    this.parent = parent;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void deleteComment() {
    this.removed = true;
  }

  public void plusLikeCount() {
    this.like_count += 1;
  }

  public void minusLikeCount() {
    this.like_count -= 1;
  }
}
