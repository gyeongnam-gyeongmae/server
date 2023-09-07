package megabrain.gyeongnamgyeongmae.auctionItem.domain.entity;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;
import megabrain.gyeongnamgyeongmae.Category.domain.entity.Category;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.UpdateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.commons.BaseTimeEntity;
import megabrain.gyeongnamgyeongmae.member.domain.entity.Member;

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

  @Column(name = "removed")
  private boolean removed = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(name = "name")
  private String name;

  @Column(name = "highest_price")
  private Long price;

  @Embedded private Content content;

  @ManyToOne(fetch = FetchType.EAGER)
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
  private AuctionStatus status = AuctionStatus.ONGOING;
  //
  //  @OneToOne(fetch = FetchType.EAGER)
  //  @JoinColumn(name = "location_id")
  //  private Location location;

  @Column private Long temperature;

  @Builder
  public AuctionItem(Long id, String name, long price, Content content, LocalDateTime closedTime) {

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
      Content content,
      LocalDateTime closedTime,
      Category category,
      Member member,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.content = content;
    this.closedTime = closedTime;
    this.category = category;
    this.member = member;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public void updateAuctionItem(UpdateAuctionItemRequest upDateAuctionItemRequest) {
    this.name = upDateAuctionItemRequest.getName();
    this.price = upDateAuctionItemRequest.getPrice();
    this.content = upDateAuctionItemRequest.getContent();
    this.closedTime = upDateAuctionItemRequest.getClosedTime();
    this.status = upDateAuctionItemRequest.getAuctionStatus();
  }

  public void removeAuctionItem(AuctionItem auctionItem) {
    this.removed = true;
  }

  public void checkShowAuctionItem(AuctionItem auctionItem) {
    if (this.removed) {
      throw new RuntimeException("삭제된 상품입니다.");
    }
  }
}
