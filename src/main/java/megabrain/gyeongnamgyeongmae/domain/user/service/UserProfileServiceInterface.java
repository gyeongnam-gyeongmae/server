package megabrain.gyeongnamgyeongmae.domain.user.service;

import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLike;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserProfile.SearchAuctionItemByUser;

public interface UserProfileServiceInterface {
  List<AuctionItemLike> findLikedAuctionItemIdsByUserId(Long userId);

  AuctionItemSearchResponse findPostAuctionItemIdsByUserId(
      SearchAuctionItemByUser searchAuctionItemByUser);
}
