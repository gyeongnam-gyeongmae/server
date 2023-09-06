package megabrain.gyeongnamgyeongmae.auctionItem.service.Search;

import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.Category.service.CategoryService;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.AuctionItemFirstView;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class AuctionItemSearchServiceImpl implements AuctionItemSearchService {

  private final CategoryService categoryService;
  private final AuctionItemRepository auctionItemRepository;

  @Override
  @Transactional(readOnly = true)
  public Page<AuctionItemFirstView> findAuctionItembyRequest(
      @RequestParam(required = false) SearchAuctionItemSortedRequest searchAuctionItemSortedRequest,
      @RequestParam Pageable pageable) {
    Page<AuctionItem> auctionItemEntityList =
        auctionItemRepository.searchAuctionItemPage(searchAuctionItemSortedRequest, pageable);
    return auctionItemEntityList.map(AuctionItemFirstView::of);
  }
}
