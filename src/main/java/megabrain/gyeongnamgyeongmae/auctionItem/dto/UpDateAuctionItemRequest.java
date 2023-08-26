package megabrain.gyeongnamgyeongmae.auctionItem.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.*;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionStatus;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.Content;

@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UpDateAuctionItemRequest {

  @NotNull private Long id;

  @NotEmpty private String name;

  @Min(1)
  private Integer price;

  @NotEmpty private String category;

  @NotNull private Content content;

  @NotNull private LocalDateTime closedTime;

  @NotNull private AuctionStatus auctionStatus;
}
