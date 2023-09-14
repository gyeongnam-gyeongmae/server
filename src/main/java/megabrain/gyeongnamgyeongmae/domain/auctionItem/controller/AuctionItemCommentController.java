package megabrain.gyeongnamgyeongmae.domain.auctionItem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemCommentRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentDeleteRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.AuctionItemCommentParentDto;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentUpdateRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Comment.AuctionItemCommentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "경매품에 답글 작성", description = "경매품 댓글 관련 API")
@RequestMapping({"api/auctions/comments"})
@RestController
@RequiredArgsConstructor
public class AuctionItemCommentController {

    private final AuctionItemCommentRepository auctionItemCommentRepository;
    private final AuctionItemCommentServiceImpl auctionItemCommentServiceImpl;

    @Operation(summary = "댓글 쓰기 ", description = "경매품 댓글 쓰기")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping({"{id}"})
    public ResponseEntity<HttpStatus> createAuctionItemComment(@PathVariable Long id, @RequestBody AuctionItemCommentRequest auctionItemCommentRequest) {
        Long memberId = auctionItemCommentRequest.getUserId();
        this.auctionItemCommentServiceImpl.createAuctionItemComment(auctionItemCommentRequest, id, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "경매품에 대한 댓글 보기", description = "경매품에 대한 경매품 댓글 보기")
    @GetMapping({"{id}"})
    public ResponseEntity<List<AuctionItemCommentParentDto>> findAuctionItemCommentById(@PathVariable Long id) {
        List<AuctionItemCommentParentDto> commentViews = this.auctionItemCommentServiceImpl.findAuctionItemCommentById(id);
        return ResponseEntity.ok(commentViews);
    }


    @Operation(summary = "경매품 댓글 수정 ", description = "경매품 댓글 수정")
    @PutMapping({""})
    public ResponseEntity<HttpStatus> updateAuctionItemComment(@RequestBody AuctionItemCommentUpdateRequest auctionItemCommentUpdateRequest) {
        this.auctionItemCommentServiceImpl.updateAuctionItemComment(auctionItemCommentUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "경매품 댓글 삭제",description = "경매품 댓글 삭제")
    @DeleteMapping({""})
    public ResponseEntity<HttpStatus> deleteAuctionItemComment(AuctionItemCommentDeleteRequest auctionItemCommentDeleteRequest) {
        this.auctionItemCommentServiceImpl.deleteAuctionItemComment(auctionItemCommentDeleteRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}


