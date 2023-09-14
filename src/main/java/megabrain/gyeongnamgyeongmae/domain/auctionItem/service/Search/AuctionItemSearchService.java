package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Search;

import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemFirstView;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuctionItemSearchService {
    Page<AuctionItemFirstView> findAuctionItembyRequest(SearchAuctionItemSortedRequest searchAuctionItemSortedRequest, Pageable pageable);
}
