package megabrain.gyeongnamgyeongmae.domain.auctionItem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemFirstView;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item.AuctionItemServiceImpl;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Search.AuctionItemSearchServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final AuctionItemServiceImpl auctionItemServiceImpl;
    private final AuctionItemRepository AuctionItemRepository;
    private final AuctionItemSearchServiceImpl auctionItemSearchServiceImpl;

    @Operation(summary = "Search AuctionItem", description = "경매품 검색하기")
    @GetMapping("")
    public ResponseEntity<Page<AuctionItemFirstView>> findItemCategory(@ModelAttribute SearchAuctionItemSortedRequest searchAuctionItemSortedRequest, Pageable pageable) {
        Page<AuctionItemFirstView> result = this.auctionItemSearchServiceImpl.findAuctionItembyRequest(searchAuctionItemSortedRequest, pageable);
        return ResponseEntity.ok(result);
    }
}