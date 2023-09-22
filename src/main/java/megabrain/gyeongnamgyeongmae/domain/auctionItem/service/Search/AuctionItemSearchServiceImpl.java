package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Search;

import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;
import megabrain.gyeongnamgyeongmae.domain.category.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuctionItemSearchServiceImpl implements AuctionItemSearchService {

    private final CategoryService categoryService;
    private final AuctionItemRepository auctionItemRepository;

    @Override
    @Transactional(readOnly = true)
    public AuctionItemSearchResponse findAuctionItemByRequest(
            SearchAuctionItemSortedRequest searchAuctionItemSortedRequest) {
        if (searchAuctionItemSortedRequest.getKeyword().equals("")) {
            searchAuctionItemSortedRequest.setKeyword(null);
        }
        if (searchAuctionItemSortedRequest.getCategory().equals("")) {
            searchAuctionItemSortedRequest.setCategory(null);
        }
        if(searchAuctionItemSortedRequest.getNick_name().equals("")){
            searchAuctionItemSortedRequest.setNick_name(null);
        }
        return auctionItemRepository.searchAuctionItemPage(searchAuctionItemSortedRequest);
    }
}
