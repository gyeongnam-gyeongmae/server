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
public class  AuctionItem extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "auction_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(name = "name")
  private String name;

  @Column(name = "highest_price")
  private int price;

  @Embedded private Content content;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "category_id")
  private Category category;

  @Column(name = "close_at")
  protected LocalDateTime closedTime;

  @Column(name = "like_count")
  private int like_count = 0;

  @Column(name = "view_count")
  private int view_count = 0;

  @Column(name = "auction_status")
  @Enumerated(EnumType.STRING)
  private AuctionStatus status = AuctionStatus.ONGOING;
//
//  @OneToOne(fetch = FetchType.EAGER)
//  @JoinColumn(name = "location_id")
//  private Location location;

  @Column
  private Long temperature;

  @Builder
  public AuctionItem(Long id, String name, int price, Content content, LocalDateTime closedTime) {

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
      int price,
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
}
