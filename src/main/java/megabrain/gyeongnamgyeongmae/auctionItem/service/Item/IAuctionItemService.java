package megabrain.gyeongnamgyeongmae.auctionItem.service.Item;

import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItem;

public interface IAuctionItemService {

  void createAuctionItem(AuctionItem auctionItem);

  AuctionItem findAuctionItemById(Long id);

  void updateAuctionItemViewCount(AuctionItem auctionItem);

  void updateAuctionItem(AuctionItem auctionItem);
}
