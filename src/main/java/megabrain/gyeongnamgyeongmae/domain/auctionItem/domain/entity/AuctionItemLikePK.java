package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class AuctionItemLikePK implements Serializable {
  private Long auctionItemId;
  private Long userId;
}
