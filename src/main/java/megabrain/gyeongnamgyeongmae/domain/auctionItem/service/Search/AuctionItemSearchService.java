package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Search;

import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemFirstView;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemPaginationDto;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface AuctionItemSearchService {

    AuctionItemSearchResponse findAuctionItemByRequest(
            SearchAuctionItemSortedRequest searchAuctionItemSortedRequest);

}
