package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionStatus;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.QAuctionItem;

@Data
public class SearchAuctionItemSortedRequest {

    private String keyword;

    private String category;

    @Schema(name = "city", example = "김해시", description = "유저 프로필 조회 시 반환되는 유저의 거주지역")
    private String city;

    @NotNull private Boolean closed;

    @NotNull private Boolean search_time;

    @NotNull private Boolean like;

    @NotNull private Boolean search_price;

    @NotNull private Long page;

  public SearchAuctionItemSortedRequest() {
    this.closed = Boolean.FALSE;
    this.search_time = Boolean.FALSE;
    this.search_price = Boolean.FALSE;
  }
}
