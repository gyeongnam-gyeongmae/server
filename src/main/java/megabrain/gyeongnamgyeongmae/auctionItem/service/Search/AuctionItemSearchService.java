package megabrain.gyeongnamgyeongmae.auctionItem.service.Search;

import megabrain.gyeongnamgyeongmae.auctionItem.dto.AuctionItemFirstView;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuctionItemSearchService {

  Page<AuctionItemFirstView> findAuctionItembyRequest(
      SearchAuctionItemSortedRequest searchAuctionItemSortedRequest, Pageable pageable);
}
