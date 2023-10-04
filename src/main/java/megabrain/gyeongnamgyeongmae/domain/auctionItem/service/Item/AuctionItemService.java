package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item;

import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemLikeRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.CreateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.UpdateAuctionItemRequest;

public interface AuctionItemService {

  void createAuctionItem(CreateAuctionItemRequest createAuctionItemRequest);

  AuctionItem findAuctionItemById(Long id);

  AuctionItemResponse auctionItemResponse(AuctionItem auctionItem, List<String> imageUrls);

  void updateAuctionItem(UpdateAuctionItemRequest upDateAuctionItemRequest, Long id);

  Boolean updateAuctionPrice(Long id, Long price);

  void deleteAuctionItemById(Long id);

  void likeAuctionItemById(Long id, AuctionItemLikeRequest auctionItemLikeRequest);

  void saveAuctionItem(AuctionItem auctionItem);
}
