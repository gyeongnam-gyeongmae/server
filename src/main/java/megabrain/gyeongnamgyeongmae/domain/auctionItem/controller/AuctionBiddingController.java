package megabrain.gyeongnamgyeongmae.domain.auctionItem.controller;

import static megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Comment.bidding.AuctionBiddingServiceService.AUCTION_PRICE_CHANNEL_NAME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.bidding.AuctionBidRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Comment.bidding.AuctionBiddingServiceInterface;
import megabrain.gyeongnamgyeongmae.domain.authentication.service.AuthenticationService;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.global.anotation.LoginRequired;
import megabrain.gyeongnamgyeongmae.global.config.RedisSsePublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Tag(name = "경매가", description = "경매가 관련 API")
@RequestMapping({"api/auctions/{id}/bids"})
@RestController
@RequiredArgsConstructor
public class AuctionBiddingController {

  private final AuctionBiddingServiceInterface auctionBiddingService;
  private final AuthenticationService authenticationService;
  private final RedisSsePublisher redisPublisher;

  @PostMapping()
  @LoginRequired
  @Operation(summary = "경매가 제시 (세션 필요 🔑)", description = "경매품에 대한 경매가를 제시합니다.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "입찰 성공"),
        @ApiResponse(responseCode = "400", description = "이미 종료된 경매입니다."),
        @ApiResponse(responseCode = "400", description = "입찰가는 현재 입찰가보다 커야합니다."),
        @ApiResponse(responseCode = "401", description = "세션 인증에 실패했습니다."),
        @ApiResponse(responseCode = "403", description = "자신의 경매에는 입찰을 요청할 수 없습니다."),
        @ApiResponse(responseCode = "409", description = "경매 입찰에 실패하였습니다.")
      })
  public ResponseEntity<HttpStatus> bidAuction(
      @PathVariable Long id, @RequestBody @Valid AuctionBidRequest auctionBidRequest) {
    User logedInUser = authenticationService.getLoginUser();
    auctionBiddingService.bidAuction(id, auctionBidRequest, logedInUser);
    redisPublisher.publish(AUCTION_PRICE_CHANNEL_NAME + id, auctionBidRequest.getPrice());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping()
  @Operation(summary = "경매가 조회", description = "경매가 반환하는 SSE를 구독합니다.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "경매가 조회 성공"),
        @ApiResponse(responseCode = "404", description = "경매가 조회 실패")
      })
  public SseEmitter subscribeAuctionPrice(@PathVariable Long id) {
    return auctionBiddingService.subscribeAuctionPrice(id);
  }
}
