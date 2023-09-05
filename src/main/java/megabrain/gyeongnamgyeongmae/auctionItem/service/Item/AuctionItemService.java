package megabrain.gyeongnamgyeongmae.auctionItem.service.Item;

import java.time.LocalDateTime;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.AuctionItemResponse;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.CreateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.UpdateAuctionItemRequest;

public interface AuctionItemService {

  void createAuctionItem(CreateAuctionItemRequest createAuctionItemRequest);

  AuctionItemResponse findAuctionItemById(Long id);

  void updateAuctionItemViewCount(AuctionItem auctionItem);

  void updateAuctionItem(UpdateAuctionItemRequest upDateAuctionItemRequest);

  void checkClosedTime(LocalDateTime closedTime);
}
