package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AuctionItemLikePK implements Serializable {
  private Long auction_id;
  private Long user_id;
}
