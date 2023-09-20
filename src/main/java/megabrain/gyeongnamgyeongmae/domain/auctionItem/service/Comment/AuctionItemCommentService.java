package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Comment;

import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentDeleteRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentUpdateRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.AuctionItemCommentParentDto;

public interface AuctionItemCommentService {

  void createAuctionItemComment(
      AuctionItemCommentRequest auctionItemCommentRequest, Long id, Long userId);

  List<AuctionItemCommentParentDto> findAuctionItemCommentById(Long id);

  void updateAuctionItemComment(AuctionItemCommentUpdateRequest auctionItemCommentUpdateRequest);

  void deleteAuctionItemComment(AuctionItemCommentDeleteRequest auctionItemCommentDeleteRequest);
}
