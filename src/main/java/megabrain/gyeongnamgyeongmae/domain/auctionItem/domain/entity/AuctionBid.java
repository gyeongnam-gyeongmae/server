package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.global.BaseTimeEntity;

@Entity
@Table(name = "auction_bids")
@IdClass(AuctionBidPK.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionBid extends BaseTimeEntity {
  @Id
  @ManyToOne(targetEntity = AuctionItem.class)
  @JoinColumn(name = "auction_id")
  private AuctionItem auction;

  @Id
  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "price", nullable = false)
  private Long price;

  private AuctionBid(AuctionItem auction, User user, Long price) {
    this.auction = auction;
    this.user = user;
    this.price = price;
  }

  public static AuctionBid of(AuctionItem auction, User user, Long price) {
    return new AuctionBid(auction, user, price);
  }
}
