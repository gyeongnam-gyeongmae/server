package megabrain.gyeongnamgyeongmae.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.CommentSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserItemSearchDto;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserProfile.SearchByUserDto;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserProfileServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "유저 프로필", description = "유저 프로필 관련  api")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/users/profile")
public class UserProfileController {

  private final UserProfileServiceInterface userProfileService;

  @GetMapping("/liked/{userId}")
  @Operation(summary = "좋아요한 게시글 조회(관심 물품 조회)", description = "유저가 좋아요한 게시글을 조회합니다.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
      })
  public ResponseEntity<List<Long>> findLikedAuctionItemIds(
      @PathVariable Long userId) {
    Long page = 1L;
    List<Long> result = userProfileService.findLikedAuctionItemIdsByUserId(userId, page);

    return ResponseEntity.ok(result);
  }

  @GetMapping("/{userId}/auctionItems")
  @Operation(summary = "유저가 등록한 경매품 조회", description = "유저가 등록한 경매품을 조회합니다.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
      })
  public ResponseEntity<AuctionItemSearchResponse> findPostAuctionItemsByUserId(
      @PathVariable Long userId, @ModelAttribute UserItemSearchDto userItemSearchDto) {
    AuctionItemSearchResponse result =
        userProfileService.findPostAuctionItemIdsByUserId(userItemSearchDto, userId);
    return ResponseEntity.ok(result);
  }

  @GetMapping("/{userId}/auctionItems/buy")
  @Operation(summary = "유저가 구매한 경매품 조회", description = "유저가 구매한 경매품을 조회합니다.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
      })
  public ResponseEntity<AuctionItemSearchResponse> findBuyAuctionItemsByUserId(
      @PathVariable Long userId, @RequestParam Long page) {
    AuctionItemSearchResponse result =
        userProfileService.findBuyAuctionItemIdsByUserId(userId, page);
    return ResponseEntity.ok(result);
  }

  //  @GetMapping("/{userId}/comments")
  //    @Operation(summary = "유저가 쓴 댓글 조회", description = "해당 유저가 등록한 댓글을 조회합니다.")
  //    @ApiResponses(
  //        value = {
  //          @ApiResponse(responseCode = "200", description = "조회 성공"),
  //        })
  //  public ResponseEntity<AuctionItemSearchResponse> findPostCommentsByUserId(
  //      @PathVariable Long userId, @ModelAttribute SearchByUserDto searchByUserDto) {
  //    AuctionItemSearchResponse result =
  //        userProfileService.findGetCommentIdsByUserId(searchByUserDto, userId);
  //    return ResponseEntity.ok(result);
  //  }

  @GetMapping("/{userId}/comments/like")
  @Operation(summary = "유저가 좋아요한 댓글 조회", description = "해당 유저가 좋아요한 댓글을 조회합니다.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
      })
  public ResponseEntity<CommentSearchResponse> findLikeCommentsByUserId(
      @PathVariable Long userId, @ModelAttribute SearchByUserDto searchByUserDto) {
    CommentSearchResponse result =
        userProfileService.findGetLikeCommentByUserId(searchByUserDto, userId);
    return ResponseEntity.ok(result);
  }
}
