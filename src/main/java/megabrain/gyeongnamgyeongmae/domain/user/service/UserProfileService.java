package megabrain.gyeongnamgyeongmae.domain.user.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLike;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItemDto;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item.AuctionItemService;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Search.AuctionItemSearchService;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserProfile.SearchAuctionItemByUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService implements UserProfileServiceInterface {

  private final AuctionItemService auctionItemService;
  private final AuctionItemSearchService auctionItemSearchService;

  @Override
  public List<AuctionItemLike> findLikedAuctionItemIdsByUserId(Long userId) {
    return auctionItemService.auctionItemLikesFindByUserId(userId);
  }

  @Override
  public AuctionItemSearchResponse findPostAuctionItemIdsByUserId(
      SearchAuctionItemByUser searchAuctionItemByUser) {
    SearchItemDto request =
        SearchItemDto.builder()
            .user_id(searchAuctionItemByUser.getUserId())
            .page(searchAuctionItemByUser.getPage())
            .build();
    return auctionItemSearchService.findAuctionItemByRequest(request);
  }
}
