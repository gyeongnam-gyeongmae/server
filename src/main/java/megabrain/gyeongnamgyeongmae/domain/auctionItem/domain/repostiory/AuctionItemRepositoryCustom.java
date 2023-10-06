package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory;

import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItemDto;

public interface AuctionItemRepositoryCustom {
  AuctionItemSearchResponse searchAuctionItemPage(SearchItemDto searchAuctionItemSortedRequest);
}
