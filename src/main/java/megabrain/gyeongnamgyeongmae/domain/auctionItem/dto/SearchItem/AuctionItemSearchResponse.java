package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemFirstView;

@Data
public class AuctionItemSearchResponse {

  private List<AuctionItemFirstView> auctionItemFirstViewPage;
  private AuctionItemPaginationDto auctionItemPaginationDto;

  @Builder
  public AuctionItemSearchResponse(
      List<AuctionItemFirstView> auctionItemFirstViewPage,
      AuctionItemPaginationDto auctionItemPaginationDto) {
    this.auctionItemFirstViewPage = auctionItemFirstViewPage;
    this.auctionItemPaginationDto = auctionItemPaginationDto;
  }
}
