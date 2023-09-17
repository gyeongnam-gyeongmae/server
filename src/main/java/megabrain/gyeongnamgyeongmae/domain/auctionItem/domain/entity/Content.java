package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Content {

  private String content;

  private AuctionItemStatus status;
}
