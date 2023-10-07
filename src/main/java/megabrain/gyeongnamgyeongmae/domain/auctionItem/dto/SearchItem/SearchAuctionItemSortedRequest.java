package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.user.dto.FindStatus;

@Data
public class SearchAuctionItemSortedRequest {

  private String keyword;

  private String category;

  @Schema(name = "city", example = "김해시", description = "유저 프로필 조회 시 반환되는 유저의 거주지역")
  private String city;

  @NotNull private FindStatus closed;

  @NotNull private Boolean search_time;

  @NotNull private Boolean like;

  @NotNull private Boolean search_price;

  @NotNull private Long page;
}
