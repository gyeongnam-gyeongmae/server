package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Comment.bidding;

import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.bidding.AuctionBidRequest;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AuctionBiddingServiceInterface {

  void bidAuction(Long auctionId, AuctionBidRequest auctionBidRequest, User user);

  SseEmitter subscribeAuctionPrice(Long auctionId);
}
