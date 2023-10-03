package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Search;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuctionItemSearchServiceImpl implements AuctionItemSearchService {

  private final AuctionItemRepository auctionItemRepository;

  @Override
  @Transactional(readOnly = true)
  public AuctionItemSearchResponse findAuctionItemByRequest(
      SearchAuctionItemSortedRequest searchAuctionItemSortedRequest) {

    setToNullIfEmpty(
        searchAuctionItemSortedRequest::getKeyword, searchAuctionItemSortedRequest::setKeyword);
    setToNullIfEmpty(
        searchAuctionItemSortedRequest::getCategory, searchAuctionItemSortedRequest::setCategory);
    setToNullIfEmpty(
        searchAuctionItemSortedRequest::getCity, searchAuctionItemSortedRequest::setCity);

    return auctionItemRepository.searchAuctionItemPage(searchAuctionItemSortedRequest);
  }

  private void setToNullIfEmpty(Supplier<String> getter, Consumer<String> setter) {
    if (Objects.equals(getter.get(), "")) {
      setter.accept(null);
    }
  }
}
