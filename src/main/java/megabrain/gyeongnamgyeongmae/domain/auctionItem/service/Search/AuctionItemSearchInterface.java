package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Search;

import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;

public interface AuctionItemSearchInterface {

  AuctionItemSearchResponse findAuctionItemByRequest(
      SearchAuctionItemSortedRequest searchAuctionItemSortedRequest);
}
