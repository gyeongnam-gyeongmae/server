package megabrain.gyeongnamgyeongmae.domain.auctionItem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.*;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item.AuctionItemService;
import megabrain.gyeongnamgyeongmae.domain.image.Service.FindImageServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "경매품 업로드", description = "경매품 작성 관련 api")
@RequestMapping({"api/auctions/"})
@RestController
@RequiredArgsConstructor
public class AuctionItemController {

    private final AuctionItemService auctionItemService;
    private final FindImageServiceInterface findImageService;

    @PostMapping("")
    @Operation(summary = "경매품 게시글 생성", description = "경매품의 정보를 업로드 합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "경매품 업로드 성공"),
                    @ApiResponse(responseCode = "404", description = "구매자 혹은 판매자를 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "올바르지 못한 값을 입력")
            })
    public ResponseEntity<CreateAuctionItemResponse> createAuctionItem(
            @RequestBody @Valid CreateAuctionItemRequest createAuctionItemRequest) {
        CreateAuctionItemResponse createAuctionItemResponse =
                CreateAuctionItemResponse.builder()
                        .id(auctionItemService.createAuctionItem(createAuctionItemRequest).getId())
                        .build();
        return ResponseEntity.ok(createAuctionItemResponse);
    }

    @GetMapping("{id}")
    @Operation(summary = "경매품 상세보기", description = "경매품에 대한 자세한 정보를 봅니다. id에 해당하는 게시글의 번호를 입력하면됩니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "경매품 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "경매품을 찾을 수 없음"),
                    @ApiResponse(responseCode = "410", description = "삭제된 경매품 입니다"),
                    @ApiResponse(responseCode = "500", description = "경매품을 찾을 수 없음")
            })
    public ResponseEntity<AuctionItemResponse> findAuctionItemById(@PathVariable Long id) {
        AuctionItem auctionItem = auctionItemService.findAuctionItemById(id);
        List<String> imageUrls = findImageService.findImageByAuctionItemIdBackUrls(id);
        AuctionItemResponse auctionItemResponse =
                auctionItemService.auctionItemResponse(auctionItem, imageUrls);
        return ResponseEntity.ok(auctionItemResponse);
    }

    @PatchMapping("{id}")
    @Operation(
            summary = "경매품 수정하기",
            description = "게시된 경매품에 대해서 수정합니다. 수정뒤에도 최소 24시간뒤에 경매를 종료할수 있습니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "경매품 수정 성공"),
                    @ApiResponse(responseCode = "404", description = "찾을 수 없음"),
                    @ApiResponse(responseCode = "410", description = "삭제된 경매품 입니다"),
                    @ApiResponse(responseCode = "500", description = "경매품 수정 실패, 올바르지 않은 값을 입력")
            })
    public ResponseEntity<HttpStatus> updateAuctionItemById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateAuctionItemRequest upDateAuctionItemRequest) {
        auctionItemService.updateAuctionItem(upDateAuctionItemRequest, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("{id}")
    @Operation(summary = "경매품 삭제하기", description = "해당 경매품을 삭제합니다")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "경매품 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "경매품을 찾을 수 없음"),
                    @ApiResponse(responseCode = "410", description = "이미 삭제된 경매품 입니다"),
            })
    public ResponseEntity<HttpStatus> deleteAuctionItemById(@PathVariable Long id) {
        auctionItemService.deleteAuctionItemById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("{id}/like")
    @Operation(summary = "경매품 좋아요(찜)", description = "경매품을 관심목록에 추가하거나 해제합니다.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "경매품 좋아요, 경매품 좋아요 취소 성공"),
                    @ApiResponse(responseCode = "404", description = "경매품을 찾을 수 없음"),
                    @ApiResponse(responseCode = "410", description = "삭제된 경매품 입니다"),
            })
    public ResponseEntity<HttpStatus> likeAuctionItemById(
            @PathVariable Long id, @RequestBody AuctionItemLikeRequest auctionItemLikeRequest) {
        auctionItemService.likeAuctionItemById(id, auctionItemLikeRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("{id}/buy")
    @Operation(summary = "경매품 구매하기", description = "경매품을 판매완료 처리합니다. body에 구매자 id를 넣어주세요 (임시코드)")
    public ResponseEntity<HttpStatus> buyAuctionItemById(
            @PathVariable Long id, @RequestBody Long BuyerId) {
        auctionItemService.buyAuctionItemById(id, BuyerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}