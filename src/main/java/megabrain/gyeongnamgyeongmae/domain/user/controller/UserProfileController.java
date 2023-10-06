package megabrain.gyeongnamgyeongmae.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLike;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserProfile.AuctionItemLikedResponse;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserProfile.SearchAuctionItemByUser;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserProfileServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 프로필", description = "유저 프로필 관련  api")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/users/profile")
public class UserProfileController {

  private final UserProfileServiceInterface userProfileService;

  @GetMapping("/liked/{userId}")
  @Operation(
      summary = "좋아요한 게시글 조회(관심 물품 조회)(임시 코드입니다)",
      description = "유저가 좋아요한 게시글을 조회합니다.(임시 코드입니다)")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
      })
  //  @LoginRequired
  public ResponseEntity<AuctionItemLikedResponse> findLikedAuctionItemIds(
      @PathVariable Long userId) {
    //    User logedIduser = authenticationService.getLoginUser();
    //    List<AuctionItemLike> auctionItemLikes =
    // userService.findLikedAuctionItemIdsByUserId(logedIduser.getId());
    List<AuctionItemLike> auctionItemLikes =
        userProfileService.findLikedAuctionItemIdsByUserId(userId);

    //    List<AuctionItemLike> auctionItemLikes =
    // auctionItemLikeRepository.AuctionLikeFindByUserId(userId);

    List<Long> auctionItemIds =
        auctionItemLikes.stream()
            .map(auctionItemLike -> auctionItemLike.getId().getAuction_id())
            .collect(Collectors.toList());

    AuctionItemLikedResponse auctionItemLikedResponse = new AuctionItemLikedResponse();
    auctionItemLikedResponse.setAuctionItemId(auctionItemIds);

    return ResponseEntity.ok(auctionItemLikedResponse);
  }

  @GetMapping("/{userId}/auctionItems")
  @Operation(summary = "유저가 등록한 경매품 조회", description = "유저가 등록한 경매품을 조회합니다.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
      })
  public ResponseEntity<AuctionItemSearchResponse> findPostAuctionItemsByUserId(
      @PathVariable Long userId, @ModelAttribute SearchAuctionItemByUser searchAuctionItemByUser) {
    AuctionItemSearchResponse result =
        userProfileService.findPostAuctionItemIdsByUserId(searchAuctionItemByUser, userId);
    return ResponseEntity.ok(result);
  }
}
