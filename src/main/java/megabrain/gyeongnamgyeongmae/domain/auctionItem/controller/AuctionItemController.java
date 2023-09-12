package megabrain.gyeongnamgyeongmae.domain.auctionItem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.transaction.Transactional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.CreateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.UpdateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item.AuctionItemService;
import megabrain.gyeongnamgyeongmae.domain.category.service.CategoryService;
import megabrain.gyeongnamgyeongmae.domain.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "경매품 게시", description = "경매품 게시 관련 API")
@RequestMapping("api/auctions")
@RestController
@RequiredArgsConstructor
public class AuctionItemController {

  private final AuctionItemService auctionItemService;
  private final CategoryService categoryService;
  private final MemberService memberService;

  @Operation(summary = "Post AuctionItem", description = "경매품 올리기")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping()
  public ResponseEntity<HttpStatus> createAuctionItem(
      @RequestBody @Valid CreateAuctionItemRequest createAuctionItemRequest) {
    auctionItemService.createAuctionItem(createAuctionItemRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Operation(summary = "Show AuctionItem", description = "경매품 상세보기")
  @GetMapping("{id}")
  public ResponseEntity<AuctionItemResponse> findAuctionItemById(@PathVariable Long id) {
    AuctionItemResponse auctionItemResponse = auctionItemService.findAuctionItemById(id);
    return ResponseEntity.ok(auctionItemResponse);
  }

  @Operation(summary = "Update AuctionItem", description = "경매품 수정하기")
  @PutMapping()
  @Transactional
  public ResponseEntity<HttpStatus> updateAuctionItemById(
      @RequestBody @Valid UpdateAuctionItemRequest upDateAuctionItemRequest) {
    auctionItemService.updateAuctionItem(upDateAuctionItemRequest);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
