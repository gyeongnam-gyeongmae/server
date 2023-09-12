package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemStatus;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Content;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateItemRequest {

  @NotNull private String name;

  @NotNull private Integer price;

  @NotNull private String category;

  @NotNull private Content content;

  @NotNull private LocalDateTime closedTime;

  @NotNull private Long member;

  public AuctionItem toEntity(CreateItemRequest createItemRequest) {
    Content content =
        Content.builder()
            .content(String.valueOf(createItemRequest.content.getContent()))
            .status(
                AuctionItemStatus.valueOf(String.valueOf(createItemRequest.content.getStatus())))
            .build();

    return AuctionItem.builder()
        .name(name)
        .price(price)
        .content(content)
        .closedTime(closedTime)
        .build();
  }
}
