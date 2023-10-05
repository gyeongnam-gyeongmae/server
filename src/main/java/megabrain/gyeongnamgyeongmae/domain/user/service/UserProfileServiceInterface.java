package megabrain.gyeongnamgyeongmae.domain.user.service;

import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLike;

public interface UserProfileServiceInterface {
  List<AuctionItemLike> findLikedAuctionItemIdsByUserId(Long userId);
}
