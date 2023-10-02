package megabrain.gyeongnamgyeongmae.domain.auctionItem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Search.AuctionItemSearchInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "경매품 검색", description = "경매품 검색 관련 API")
@RequestMapping({"api/auctions/search"})
@RestController
@RequiredArgsConstructor
public class AuctionItemSearchController {

  private final AuctionItemSearchInterface auctionItemSearchService;

  @GetMapping("")
  @Operation(summary = "경매품 검색하기", description = "경매품을 찾습니다.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "경매품 검색 성공"),
        @ApiResponse(responseCode = "500", description = "게시글을 찾지 못했습니다(서버 오류)"),
      })
  public ResponseEntity<AuctionItemSearchResponse> findItemCategory(
      @ModelAttribute SearchAuctionItemSortedRequest searchAuctionItemSortedRequest) {
    AuctionItemSearchResponse result =
        auctionItemSearchService.findAuctionItemByRequest(searchAuctionItemSortedRequest);
    return ResponseEntity.ok(result);
  }
}
