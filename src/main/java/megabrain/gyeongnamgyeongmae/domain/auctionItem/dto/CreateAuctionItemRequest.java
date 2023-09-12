package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Content;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateAuctionItemRequest {

  @NotNull private String name;

  @NotNull private Integer price;

  @NotNull private String category;

  @NotNull private Content content;

  @NotNull private LocalDateTime closedTime;

  @NotNull private Long member;

  public AuctionItem toEntity() {
    Content content =
        Content.builder()
            .content(this.content.getContent())
            .status(this.content.getStatus())
            .build();

    return AuctionItem.builder()
        .name(name)
        .price(price)
        .content(content)
        .closedTime(closedTime)
        .build();
  }
}
