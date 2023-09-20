package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemStatus;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateAuctionItemRequest {

  @NotNull private String name;

  @Min(0)
  @NotNull
  private Long price;

  @NotNull private String category;

  @NotNull private String content;

  @NotNull private AuctionItemStatus status;

  @NotNull private LocalDateTime closedTime;

  @NotNull private Long userId;

  public AuctionItem toEntity() {
    return AuctionItem.builder()
        .name(this.name)
        .price(this.price)
        .content(this.content)
        .itemStatus(this.status)
        .closedTime(this.closedTime)
        .build();
  }
}
