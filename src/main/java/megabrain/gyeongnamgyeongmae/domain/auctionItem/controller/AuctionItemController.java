package megabrain.gyeongnamgyeongmae.domain.auctionItem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.CreateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.UpdateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item.AuctionItemServiceImpl;
import megabrain.gyeongnamgyeongmae.domain.category.service.CategoryService;
import megabrain.gyeongnamgyeongmae.domain.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "경매품 업로드", description = "경매품 작성 관련 api")
@RequestMapping({"api/auctions/"})
@RestController
@RequiredArgsConstructor
public class AuctionItemController {

    private final AuctionItemServiceImpl auctionItemServiceImpl;
    private final CategoryService categoryService;
    private final MemberService memberService;

    @Operation(summary = "Post AuctionItem", description = "경매품 올리기")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public ResponseEntity<HttpStatus> createAuctionItem(@RequestBody @Valid CreateAuctionItemRequest createAuctionItemRequest) {
        this.auctionItemServiceImpl.createAuctionItem(createAuctionItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Show AuctionItem", description = "경매품 상세보기")
    @GetMapping("{id}")
    public ResponseEntity<AuctionItemResponse> findAuctionItemById(@PathVariable Long id) {
        AuctionItemResponse auctionItemResponse = auctionItemServiceImpl.findAuctionItemById(id);
        return ResponseEntity.ok(auctionItemResponse);
    }

    @Operation(summary = "Update AuctionItem", description = "경매품 수정하기")
    @PutMapping("{id}")
    public ResponseEntity<HttpStatus> updateAuctionItemById(@PathVariable Long id, @RequestBody @Valid UpdateAuctionItemRequest upDateAuctionItemRequest) {
        this.auctionItemServiceImpl.updateAuctionItem(upDateAuctionItemRequest, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @Operation(summary = "Delete AuctionItem", description = "경매품 삭제하기")
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteAuctionItemById(@PathVariable Long id) {
        this.auctionItemServiceImpl.deleteAuctionItemById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
