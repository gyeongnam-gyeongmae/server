package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory;

import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;

public interface AuctionItemRepositoryCustom {
  AuctionItemSearchResponse searchAuctionItemPage(
      SearchAuctionItemSortedRequest searchAuctionItemSortedRequest);
}
