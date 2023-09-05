package megabrain.gyeongnamgyeongmae.auctionItem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.AuctionItemFirstView;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;
import megabrain.gyeongnamgyeongmae.auctionItem.service.Search.AuctionItemSearchServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Tag(name = "경매품 검색", description = "경매품 검색 관련 API")
@RequestMapping("api/auctions/search")
@RestController
@RequiredArgsConstructor
public class AuctionItemSearch {

  private final AuctionItemSearchServiceImpl auctionItemSearchServiceImpl;
  private final AuctionItemRepository auctionItemRepository;

  @Operation(summary = "Search AuctionItem", description = "경매품 검색하기")
  @PostMapping("")
  @Transactional(readOnly = true)
  public ResponseEntity<Page<AuctionItemFirstView>> findItemCategory(
      @RequestBody @Valid SearchAuctionItemSortedRequest searchAuctionItemSortedRequest,
      Pageable pageable) {
    Page<AuctionItemFirstView> result =
        auctionItemSearchServiceImpl.findAuctionItembyRequest(
            searchAuctionItemSortedRequest, pageable);

    return ResponseEntity.ok(result);
  }
}
