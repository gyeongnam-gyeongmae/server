package megabrain.gyeongnamgyeongmae.domain.auctionItem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Search.AuctionItemSearchService;
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

  private final AuctionItemSearchService auctionItemSearchService;

  @Operation(summary = "Search AuctionItem", description = "경매품 검색하기")
  @GetMapping("")
  public ResponseEntity<AuctionItemSearchResponse> findItemCategory(
      @ModelAttribute SearchAuctionItemSortedRequest searchAuctionItemSortedRequest) {
    AuctionItemSearchResponse result =
        this.auctionItemSearchService.findAuctionItemByRequest(searchAuctionItemSortedRequest);
    return ResponseEntity.ok(result);
  }
}
