package megabrain.gyeongnamgyeongmae.auctionItem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.transaction.Transactional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.Category.domain.entity.Category;
import megabrain.gyeongnamgyeongmae.Category.service.CategoryService;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.AuctionItemResponse;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.CreateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.auctionItem.service.Item.AuctionItemService;
import megabrain.gyeongnamgyeongmae.member.domain.entity.Member;
import megabrain.gyeongnamgyeongmae.member.service.MemberService;
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
  @Transactional
  public ResponseEntity<HttpStatus> createAuctionItem(
      @RequestBody @Valid CreateAuctionItemRequest createAuctionItemRequest) {
    Member memberEntity = memberService.findMemberById(createAuctionItemRequest.getMember());
    Category categoryEntity =
        categoryService.findCategoryByName(createAuctionItemRequest.getCategory());

    AuctionItem auctionItem = createAuctionItemRequest.toEntity();
    auctionItem.setMember(memberEntity);
    auctionItem.setCategory(categoryEntity);
    auctionItemService.createAuctionItem(auctionItem);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Operation(summary = "Show AuctionItem", description = "경매품 상세보기")
  @GetMapping("{id}")
  public ResponseEntity<AuctionItemResponse> findAuctionItemById(@PathVariable Long id) {
    AuctionItem auctionItem = auctionItemService.findAuctionItemById(id);
    auctionItemService.updateAuctionItemViewCount(auctionItem);
    return ResponseEntity.ok(AuctionItemResponse.of(auctionItem));
  }
}
