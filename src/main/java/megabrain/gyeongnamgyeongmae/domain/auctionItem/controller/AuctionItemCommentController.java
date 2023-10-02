package megabrain.gyeongnamgyeongmae.domain.auctionItem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentDeleteRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentUpdateRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.AuctionItemCommentParentDto;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Comment.AuctionItemCommentServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "경매품에 답글 작성", description = "경매품 댓글 관련 API")
@RequestMapping({"api/auctions/comments"})
@RestController
@RequiredArgsConstructor
public class AuctionItemCommentController {

  private final AuctionItemCommentServiceInterface auctionItemCommentService;

  @PostMapping({"{id}"})
  @Operation(summary = "게시된 경매품에 댓글 작성하기 ", description = "경매품에 대해 댓글을 작성합니다.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "댓글 작성 성공"),
        @ApiResponse(responseCode = "404", description = "경매품을 찾을 수 없음"),
        @ApiResponse(responseCode = "410", description = "삭제된 경매품 입니다"),
      })
  public ResponseEntity<HttpStatus> createAuctionItemComment(
      @PathVariable Long id, @RequestBody AuctionItemCommentRequest auctionItemCommentRequest) {
    auctionItemCommentService.createAuctionItemComment(auctionItemCommentRequest, id);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping({"{id}"})
  @Operation(summary = "게시된 경매품에 대한 댓글 보기", description = "경매품에 대한 경매품 댓글 보기")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "댓글 작성 성공"),
        @ApiResponse(responseCode = "404", description = "경매품을 찾을 수 없음"),
        @ApiResponse(responseCode = "410", description = "삭제된 경매품 입니다"),
      })
  public ResponseEntity<List<AuctionItemCommentParentDto>> findAuctionItemCommentById(
      @PathVariable Long id) {
    List<AuctionItemCommentParentDto> commentViews =
        auctionItemCommentService.findAuctionItemCommentById(id);
    return ResponseEntity.ok(commentViews);
  }

  @PatchMapping({""})
  @Operation(summary = "작성된 댓글 수정", description = "게시된 댓글에 대해 수정하기")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "댓글 작성 성공"),
        @ApiResponse(responseCode = "404", description = "경매품을 찾을 수 없음"),
        @ApiResponse(responseCode = "410", description = "삭제된 경매품 입니다"),
      })
  public ResponseEntity<HttpStatus> updateAuctionItemComment(
      @RequestBody AuctionItemCommentUpdateRequest auctionItemCommentUpdateRequest) {
    auctionItemCommentService.updateAuctionItemComment(auctionItemCommentUpdateRequest);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @DeleteMapping({""})
  @Operation(summary = "작성된 댓글 삭제", description = "게시된 댓글에 대해 삭제하기")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "댓글 작성 성공"),
        @ApiResponse(responseCode = "404", description = "경매품을 찾을 수 없음"),
        @ApiResponse(responseCode = "410", description = "삭제된 경매품 입니다"),
      })
  public ResponseEntity<HttpStatus> deleteAuctionItemComment(
      AuctionItemCommentDeleteRequest auctionItemCommentDeleteRequest) {
    auctionItemCommentService.deleteAuctionItemComment(auctionItemCommentDeleteRequest);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
