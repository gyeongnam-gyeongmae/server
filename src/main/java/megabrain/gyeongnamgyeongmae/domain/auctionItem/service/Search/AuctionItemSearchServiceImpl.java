package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Search;

import lombok.RequiredArgsConstructor;

import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemFirstView;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;
import megabrain.gyeongnamgyeongmae.domain.category.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuctionItemSearchServiceImpl implements AuctionItemSearchService {

  private final CategoryService categoryService;
  private final AuctionItemRepository auctionItemRepository;

  @Override
  @Transactional(readOnly = true)
  public Page<AuctionItemFirstView> findAuctionItembyRequest(
          SearchAuctionItemSortedRequest searchAuctionItemSortedRequest, Pageable pageable) {
    Page<AuctionItem> auctionItemEntityList =
        auctionItemRepository.searchAuctionItemPage(searchAuctionItemSortedRequest, pageable);
    return auctionItemEntityList.map(AuctionItemFirstView::of);
  }
}
