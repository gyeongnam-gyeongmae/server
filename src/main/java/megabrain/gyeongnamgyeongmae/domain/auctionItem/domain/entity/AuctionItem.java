package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.UpdateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.domain.category.domain.entity.Category;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.global.BaseTimeEntity;

@Getter
@Setter
@Entity
@Table(name = "AuctionItem")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionItem extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "auction_id")
  private Long id;

  @Column(name = "removed", nullable = false, columnDefinition = "boolean default false")
  private boolean removed = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "name")
  private String name;

  @Column(name = "highest_price")
  private Long price;

  @Column(name = "content")
  private String content;

  @Column(name = "item_status")
  private AuctionItemStatus itemStatus;

  @ManyToOne(fetch = FetchType.EAGER, targetEntity = Category.class)
  @JoinColumn(name = "category_id")
  private Category category;

  @Column(name = "close_at")
  protected LocalDateTime closedTime;

  @Column(name = "like_count")
  private Long like_count = 0L;

  @Column(name = "view_count")
  private Long view_count = 0L;

  @Column(name = "auction_status")
  @Enumerated(EnumType.STRING)
  private AuctionStatus status;

  @Column(name = "comment_count")
  private Long comment_count;

  @Column private Long temperature;

  @Builder
  public AuctionItem(
      Long id,
      String name,
      long price,
      String content,
      AuctionItemStatus itemStatus,
      LocalDateTime closedTime) {
    this.status = AuctionStatus.ONGOING;
    this.comment_count = 0L;
    this.id = id;
    this.name = name;
    this.price = price;
    this.content = content;
    this.itemStatus = itemStatus;
    this.closedTime = closedTime;
  }

  @Builder
  private AuctionItem(
      String name,
      long price,
      String content,
      AuctionItemStatus itemStatus,
      LocalDateTime localDateTime) {
    this.name = name;
    this.price = price;
    this.content = content;
    this.itemStatus = itemStatus;
    this.closedTime = localDateTime;
    this.status = AuctionStatus.ONGOING;
    this.comment_count = 0L;
  }

  @Builder
  public AuctionItem(
      Long id,
      String name,
      long price,
      String content,
      AuctionItemStatus itemStatus,
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
    this.itemStatus = itemStatus;
    this.closedTime = closedTime;
    this.category = category;
    this.user = user;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public void removeAuctionItem(AuctionItem auctionItem) {
    this.removed = true;
  }

  public void checkShowAuctionItem(AuctionItem auctionItem) {
    if (this.removed) {
      throw new RuntimeException("삭제된 상품입니다.");
    }
  }

  public void updateAuctionItem(UpdateAuctionItemRequest upDateAuctionItemRequest) {
    this.name = upDateAuctionItemRequest.getName();
    this.price = upDateAuctionItemRequest.getPrice();
    this.content = upDateAuctionItemRequest.getContent();
    this.itemStatus = upDateAuctionItemRequest.getItemStatus();
    this.closedTime = upDateAuctionItemRequest.getClosedTime();
    this.status = upDateAuctionItemRequest.getAuctionStatus();
  }
}
