package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Search;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItemDto;
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
    SearchItemDto searchItemDto = SearchItemDto.of(searchAuctionItemSortedRequest);
    return auctionItemRepository.searchAuctionItemPage(searchItemDto);
  }

  private void setToNullIfEmpty(Supplier<String> getter, Consumer<String> setter) {
    if (Objects.equals(getter.get(), "")) {
      setter.accept(null);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public AuctionItemSearchResponse findAuctionItemByRequest(SearchItemDto searchItemDto) {
    return auctionItemRepository.searchAuctionItemPage(searchItemDto);
  }
}
