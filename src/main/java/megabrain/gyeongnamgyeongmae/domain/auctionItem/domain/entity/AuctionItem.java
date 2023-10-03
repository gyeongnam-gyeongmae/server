package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.UpdateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.exception.AuctionRemovedException;
import megabrain.gyeongnamgyeongmae.domain.category.domain.entity.Category;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.global.BaseTimeEntity;

@Getter
@Entity
@Table(name = "auction_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionItem extends BaseTimeEntity {

  @Column(name = "close_at")
  protected LocalDateTime closedTime;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "auction_id")
  private Long id;

  @Column(name = "removed", nullable = false, columnDefinition = "boolean default false")
  private boolean removed = false;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "name")
  private String name;

  @Column(name = "highest_price")
  private Long price;

  @Column(name = "content")
  private String content;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "category_id")
  private Category category;

  @Column(name = "like_count")
  private Long like_count = 0L;

  @Column(name = "view_count")
  private Long view_count = 0L;

  @Column(name = "auction_status")
  @Enumerated(EnumType.STRING)
  private AuctionStatus status;

  @Column(name = "comment_count")
  private Long comment_count;

  @Builder
  public AuctionItem(Long id, String name, long price, String content, LocalDateTime closedTime) {
    this.status = AuctionStatus.ONGOING;
    this.comment_count = 0L;
    this.id = id;
    this.name = name;
    this.price = price;
    this.content = content;
    this.closedTime = closedTime;
  }

  @Builder
  public AuctionItem(
      Long id,
      String name,
      long price,
      String content,
      LocalDateTime closedTime,
      Category category,
      User user,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.status = AuctionStatus.ONGOING;
    this.comment_count = 0L;
    this.id = id;
    this.name = name;
    this.price = price;
    this.content = content;
    this.closedTime = closedTime;
    this.category = category;
    this.user = user;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public void minusLikeCount() {
    this.like_count -= 1;
  }

  public void plusLikeCount() {
    this.like_count += 1;
  }

  public void plusViewCount() {
    this.view_count += 1;
  }

  public void plusCommentCount() {
    this.comment_count += 1;
  }

  public void minusCommentCount() {
    this.comment_count -= 1;
  }

  public void removeAuctionItem(AuctionItem auctionItem) {
    this.removed = true;
  }

  public void checkShowAuctionItem(AuctionItem auctionItem) {
    if (this.removed) {
      throw new AuctionRemovedException("삭제된 경매품 입니다.");
    }
  }

  public void updateAuctionItem(UpdateAuctionItemRequest upDateAuctionItemRequest) {
    this.name = upDateAuctionItemRequest.getName();
    this.price = upDateAuctionItemRequest.getPrice();
    this.content = upDateAuctionItemRequest.getContent();
    this.closedTime = upDateAuctionItemRequest.getClosedTime();
  }
}
