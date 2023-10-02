package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity;

import javax.persistence.*;
import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.global.BaseTimeEntity;

@Getter
@Entity
@Table(name = "auction_item_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionItemLike extends BaseTimeEntity {

  @EmbeddedId private AuctionItemLikePK id;

  @MapsId("auction_id")
  @ManyToOne
  @JoinColumn(name = "auction_id")
  private AuctionItem auctionItem;

  @MapsId("user_id")
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Builder
  public AuctionItemLike(AuctionItemLikePK id, AuctionItem auctionItem, User user) {
    this.id = id;
    this.auctionItem = auctionItem;
    this.user = user;
  }
}
