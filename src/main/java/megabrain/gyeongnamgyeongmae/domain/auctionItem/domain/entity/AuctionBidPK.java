package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuctionBidPK implements Serializable {
  private Long auction;
  private Long user;
}
