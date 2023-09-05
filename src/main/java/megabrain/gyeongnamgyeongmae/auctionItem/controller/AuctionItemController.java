package megabrain.gyeongnamgyeongmae.auctionItem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.Category.service.CategoryService;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.*;
import megabrain.gyeongnamgyeongmae.auctionItem.service.Item.AuctionItemServiceImpl;
import megabrain.gyeongnamgyeongmae.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "경매품 게시", description = "경매품 게시 관련 API")
@RequestMapping("api/auctions")
@RestController
@RequiredArgsConstructor
public class AuctionItemController {

  private final AuctionItemServiceImpl auctionItemServiceImpl;
  private final CategoryService categoryService;
  private final MemberService memberService;

  @Operation(summary = "Post AuctionItem", description = "경매품 올리기")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping()
  public ResponseEntity<HttpStatus> createAuctionItem(
      @RequestBody @Valid CreateAuctionItemRequest createAuctionItemRequest) {
    auctionItemServiceImpl.createAuctionItem(createAuctionItemRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Operation(summary = "Show AuctionItem", description = "경매품 상세보기")
  @GetMapping("{id}")
  public ResponseEntity<AuctionItemResponse> findAuctionItemById(@PathVariable Long id) {
    AuctionItemResponse auctionItemResponse = auctionItemServiceImpl.findAuctionItemById(id);
    return ResponseEntity.ok(auctionItemResponse);
  }

  @Operation(summary = "Update AuctionItem", description = "경매품 수정하기")
  @PutMapping()
  public ResponseEntity<HttpStatus> updateAuctionItemById(
      @RequestBody @Valid UpdateAuctionItemRequest upDateAuctionItemRequest) {
    auctionItemServiceImpl.updateAuctionItem(upDateAuctionItemRequest);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
