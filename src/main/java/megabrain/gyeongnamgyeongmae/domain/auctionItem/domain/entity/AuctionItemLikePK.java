package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class AuctionItemLikePK implements Serializable {
    private Long auctionItemId;
    private Long memberId;

}
