package megabrain.gyeongnamgyeongmae.auctionItem.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.Category.domain.entity.Category;
import megabrain.gyeongnamgyeongmae.Category.service.CategoryService;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.CreateItemRequest;
import megabrain.gyeongnamgyeongmae.auctionItem.service.Item.AuctionItemService;
import megabrain.gyeongnamgyeongmae.member.domain.entity.Member;
import megabrain.gyeongnamgyeongmae.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//    뷰 -> (컨트롤러 -> 서비스) -> DB

@RequestMapping("api/auctions")
@RestController
@RequiredArgsConstructor
public class AuctionItemController {

  private final AuctionItemService auctionItemService;
  private final CategoryService categoryService;
  private final MemberService memberService;

  @PostMapping()
  public ResponseEntity<HttpStatus> createAuctionItem(
      @RequestBody @Valid CreateItemRequest createItemRequest) {
    Member memberEntity = memberService.findMemberById(createItemRequest.getMember());
    Category categoryEntity = categoryService.findCategoryByName(createItemRequest.getCategory());

    AuctionItem auctionItem = createItemRequest.toEntity(createItemRequest);
    auctionItem.setMember(memberEntity);
    auctionItem.setCategory(categoryEntity);
    auctionItemService.createAuctionItem(auctionItem);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
