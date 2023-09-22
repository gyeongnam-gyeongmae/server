package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item;

import java.time.LocalDateTime;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemLikeRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.CreateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.UpdateAuctionItemRequest;

public interface AuctionItemService {

  void createAuctionItem(CreateAuctionItemRequest createAuctionItemRequest);

  AuctionItemResponse findAuctionItemById(Long id);

  void updateAuctionItemViewCount(AuctionItem auctionItem);

  void updateAuctionItem(UpdateAuctionItemRequest upDateAuctionItemRequest, Long id);

  void checkClosedTime(LocalDateTime closedTime);

  void deleteAuctionItemById(Long id);

  void likeAuctionItemById(Long id, AuctionItemLikeRequest auctionItemLikeRequest);
}
