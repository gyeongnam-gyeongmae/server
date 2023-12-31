package megabrain.gyeongnamgyeongmae.domain.user.service;

import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.CommentSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.authentication.dto.UserProfileResponse;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserItemSearchDto;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserProfile.SearchByUserDto;

import java.util.List;

public interface UserProfileServiceInterface {
  AuctionItemSearchResponse findLikedAuctionItemIdsByUserId(Long userId, Long page);

  AuctionItemSearchResponse findPostAuctionItemIdsByUserId(
      UserItemSearchDto userItemSearchDto, Long userId);

  CommentSearchResponse findGetLikeCommentByUserId(Long userId, Long page);

  AuctionItemSearchResponse findBuyAuctionItemIdsByUserId(Long userId, Long page);

  UserProfileResponse getUserProfile(User user);
}
