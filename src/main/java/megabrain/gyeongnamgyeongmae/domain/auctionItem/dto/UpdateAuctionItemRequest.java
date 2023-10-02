package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UpdateAuctionItemRequest {

  @NotEmpty private String name;

  @NotNull private Long userId;

  @Min(0)
  private Long price;

  @NotEmpty private String category;

  @NotEmpty private String content;

  @NotNull private LocalDateTime closedTime;
}
