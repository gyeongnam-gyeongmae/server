package megabrain.gyeongnamgyeongmae.domain.user.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLike;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemCommentRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.CommentLikeRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.CommentLikeRepositoryCustom;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.CommentSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItemDto;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Comment.AuctionItemCommentService;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item.AuctionItemService;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Search.AuctionItemSearchService;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserItemSearchDto;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserProfile.SearchByUserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService implements UserProfileServiceInterface {

  private final AuctionItemService auctionItemService;
  private final AuctionItemSearchService auctionItemSearchService;
  private final AuctionItemCommentService auctionItemCommentService;
  private final CommentLikeRepository commentLikeRepository;

  @Override
  public List<AuctionItemLike> findLikedAuctionItemIdsByUserId(Long userId) {
    return auctionItemService.auctionItemLikesFindByUserId(userId);
  }

  @Override
  public AuctionItemSearchResponse findPostAuctionItemIdsByUserId(
          UserItemSearchDto userItemSearchDto, Long userId) {
    SearchItemDto request =
        SearchItemDto.builder()
            .user_id(userId)
            .page(userItemSearchDto.getPage())
            .closed(userItemSearchDto.getClosed())
            .build();
    return auctionItemSearchService.findAuctionItemByRequest(request);
  }

  @Override
  @Transactional(readOnly = true)
  public CommentSearchResponse findGetLikeCommentByUserId(SearchByUserDto searchByUserDto, Long userId){
    return  commentLikeRepository.searchCommentLikePage(searchByUserDto, userId);
  }
}
