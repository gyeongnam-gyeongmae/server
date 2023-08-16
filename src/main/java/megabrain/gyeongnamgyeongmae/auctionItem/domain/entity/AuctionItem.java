package megabrain.gyeongnamgyeongmae.auctionItem.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.*;
import megabrain.gyeongnamgyeongmae.AuctionOffer.domain.entity.AuctionOffers;
import megabrain.gyeongnamgyeongmae.Category.domain.entity.Category;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(name = "name")
  private String name;

  @Column(name = "highest_price") // 가격
  private int price;

  @Embedded private Content content;

  @OneToOne(fetch = FetchType.EAGER) // 즉시로딩
  @JoinColumn(name = "category_id")
  private Category category;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "id")
  private List<AuctionOffers> transactions = new ArrayList<>();

  @Column(name = "close_at")
  protected LocalDateTime closedTime;

  @Column(name = "like_count")
  private int like_count = 0;

  @Column(name = "view_count")
  private int view_count = 0;

  @Column(name = "auction_status")
  @Enumerated(EnumType.STRING)
  private AuctionStatus status = AuctionStatus.ONGOING;

  @Builder
  public AuctionItem(Long id, String name, int price, Content content, LocalDateTime closedTime) {

    this.id = id;
    this.name = name;
    this.price = price;
    this.content = content;
    this.closedTime = closedTime;
  }
}
