package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Comment;

import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Comment;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentDeleteRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemCommentUpdateRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.AuctionItemCommentParentDto;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.CommentLikeDto;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.CommentSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserProfile.SearchByUserDto;

public interface AuctionItemCommentService {

  void createAuctionItemComment(AuctionItemCommentRequest auctionItemCommentRequest, Long id);

  List<AuctionItemCommentParentDto> findAuctionItemCommentById(Long id, Long finderid);

  Comment findCommentById(Long id);

  void updateAuctionItemComment(AuctionItemCommentUpdateRequest auctionItemCommentUpdateRequest);

  void deleteAuctionItemComment(AuctionItemCommentDeleteRequest auctionItemCommentDeleteRequest);

  void likeAuctionItemComment(CommentLikeDto commentLikeDto);

}
