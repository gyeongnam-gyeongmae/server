package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity;

import javax.persistence.*;
import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.global.BaseTimeEntity;

@Getter
@Setter
@Entity
@Table(name = "AuctionItemLike")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuctionItemLike extends BaseTimeEntity {

  @EmbeddedId private AuctionItemLikePK id;

  @MapsId("auctionItemId")
  @ManyToOne
  @JoinColumn(name = "auctionItemId")
  private AuctionItem auctionItem;

  @MapsId("userId")
  @ManyToOne
  @JoinColumn(name = "userId")
  private User user;

  @Builder
  public AuctionItemLike(AuctionItemLikePK id, AuctionItem auctionItem, User user) {
    this.id = id;
    this.auctionItem = auctionItem;
    this.user = user;
  }
}
