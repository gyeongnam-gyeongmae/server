package megabrain.gyeongnamgyeongmae.domain.user.service;

import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLike;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.CommentSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserItemSearchDto;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserProfile.SearchByUserDto;

public interface UserProfileServiceInterface {
  List<AuctionItemLike> findLikedAuctionItemIdsByUserId(Long userId);

  AuctionItemSearchResponse findPostAuctionItemIdsByUserId(
          UserItemSearchDto userItemSearchDto, Long userId);


  CommentSearchResponse findGetLikeCommentByUserId(SearchByUserDto searchByUserDto, Long userId);
}
