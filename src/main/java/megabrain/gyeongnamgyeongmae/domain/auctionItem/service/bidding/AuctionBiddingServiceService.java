package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.bidding;

import java.io.IOException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionBid;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionBidRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.SseEmitterRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.bidding.AuctionBidRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.exception.AuctionBidMustBeGreater;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.exception.AuctionOwnerCanNotBid;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item.AuctionItemService;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.global.config.RedisSseSubscriber;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class AuctionBiddingServiceService implements AuctionBiddingServiceInterface {
  private final AuctionItemService auctionItemService;
  private final AuctionBidRepository auctionBidRepository;
  private final RedisSseSubscriber redisSubscriber;
  private final SseEmitterRepository sseEmitterRepository;
  public static final String AUCTION_PRICE_CHANNEL_NAME = "AUCTION_PRICE_CHANNEL_NAME";
  private final Long DEFAULT_TIMEOUT = 10L * 60 * 1000; // 10분

  @Override
  @Transactional
  public void bidAuction(Long auctionId, AuctionBidRequest auctionBidRequest, User user) {
    AuctionItem auction = auctionItemService.findAuctionItemById(auctionId);
    if (auction.getUser().getId().equals(user.getId()))
      throw new AuctionOwnerCanNotBid("자신의 경매에는 입찰을 요청할 수 없습니다.");
    if (auction.isClosed()) throw new AuctionBidMustBeGreater("이미 종료된 경매입니다.");

    if (auction.getPrice() != null && auctionBidRequest.getPrice() <= auction.getPrice())
      throw new AuctionBidMustBeGreater("입찰가는 현재 입찰가보다 커야합니다.");

    Boolean isUpdated =
        auctionItemService.updateAuctionPrice(auctionId, auctionBidRequest.getPrice());
    if (!isUpdated) throw new InternalException("경매 입찰에 실패하였습니다.");

    AuctionBid auctionBid = AuctionBid.of(auction, user, auctionBidRequest.getPrice());

    auctionBidRepository.save(auctionBid);
  }

  @Override
  public SseEmitter subscribeAuctionPrice(Long auctionId) {
    String channelName = AUCTION_PRICE_CHANNEL_NAME + auctionId;
    redisSubscriber.createChannel(channelName);
    SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

    sseEmitterRepository.save(channelName, sseEmitter);

    try {
      sseEmitter.send(SseEmitter.event().data("init"));
    } catch (IOException e) {
      sseEmitterRepository.delete(channelName, sseEmitter);
    }
    return sseEmitter;
  }
}
