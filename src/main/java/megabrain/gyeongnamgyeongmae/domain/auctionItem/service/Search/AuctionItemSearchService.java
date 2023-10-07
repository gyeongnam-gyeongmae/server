package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Search;

import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItemDto;

public interface AuctionItemSearchService {

  AuctionItemSearchResponse findAuctionItemByRequest(
      SearchAuctionItemSortedRequest searchAuctionItemSortedRequest);

  AuctionItemSearchResponse findAuctionItemByRequest(SearchItemDto searchItemDto);

}
