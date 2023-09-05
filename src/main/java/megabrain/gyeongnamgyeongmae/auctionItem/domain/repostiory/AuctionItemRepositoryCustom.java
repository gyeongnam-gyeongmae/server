package megabrain.gyeongnamgyeongmae.auctionItem.domain.repostiory;

import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuctionItemRepositoryCustom {

  Page<AuctionItem> searchAuctionItemPage(
      SearchAuctionItemSortedRequest searchAuctionItemSortedRequest, Pageable pageable);
}
