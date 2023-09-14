package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Comment;

import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentDeleteRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.AuctionItemCommentParentDto;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentUpdateRequest;

import java.util.List;

public interface AuctionItemCommentService {

    void createAuctionItemComment(AuctionItemCommentRequest auctionItemCommentRequest, Long id, Long memberId);

    List<AuctionItemCommentParentDto> findAuctionItemCommentById(Long id);

    void updateAuctionItemComment(AuctionItemCommentUpdateRequest auctionItemCommentUpdateRequest);

    void deleteAuctionItemComment(AuctionItemCommentDeleteRequest auctionItemCommentDeleteRequest);
}
