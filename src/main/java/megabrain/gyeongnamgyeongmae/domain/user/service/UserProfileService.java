package megabrain.gyeongnamgyeongmae.domain.user.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLike;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLikePK;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemLikeRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.CommentLikeRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemFirstView;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.CommentSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemPaginationDto;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItemDto;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Comment.AuctionItemCommentService;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item.AuctionItemService;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Search.AuctionItemSearchService;
import megabrain.gyeongnamgyeongmae.domain.image.Service.FindImageServiceInterface;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserItemSearchDto;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserProfile.SearchByUserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService implements UserProfileServiceInterface {

  public final FindImageServiceInterface findImageService;
  private final AuctionItemService auctionItemService;
  private final AuctionItemSearchService auctionItemSearchService;
  private final AuctionItemCommentService auctionItemCommentService;
  private final CommentLikeRepository commentLikeRepository;
  private final AuctionItemRepository auctionItemRepository;
  private final FindImageServiceInterface findImageService;
  private final AuctionItemLikeRepository auctionItemLikeRepository;

  @Override
  public AuctionItemSearchResponse findLikedAuctionItemIdsByUserId(Long userId, Long page) {

    Long itemsPerPage = 10L;
    List<AuctionItemLike> auctionItemLikes =
            auctionItemService.auctionItemLikesFindByUserId(userId);
    List<AuctionItem> auctionItems = auctionItemService.auctionItemFindByIds(auctionItemLikes);

    Long totalItems = (long) auctionItems.size();

    Long startIndex = (page - 1) * itemsPerPage;
    Long endIndex = Math.min(startIndex + itemsPerPage, totalItems);

    List<AuctionItem> currentPageAuctionItems = auctionItems.subList(startIndex.intValue(), endIndex.intValue());
    List<AuctionItemFirstView> firstViews = convertResultsToViews(currentPageAuctionItems,userId);

    Long itemCount = (long) firstViews.size();


    AuctionItemPaginationDto paginationInfo = new AuctionItemPaginationDto();
    paginationInfo.setCurrentPage(page);
    paginationInfo.setItemCount(itemCount);
    paginationInfo.setItemsPerPage(itemsPerPage);
    paginationInfo.setTotalItems(totalItems);
    paginationInfo.setTotalPages((totalItems + itemsPerPage - 1) / itemsPerPage);

    return AuctionItemSearchResponse.builder()
            .auctionItemFirstViewPage(firstViews)
            .auctionItemPaginationDto(paginationInfo)
            .build();
  }


  private List<AuctionItemFirstView> convertResultsToViews(List<AuctionItem> results, Long userId){
    return results.stream()
            .map(result -> {
              boolean isPresent = auctionItemLikeRepository.findById(new AuctionItemLikePK(result.getId(), userId)).isPresent();
              return AuctionItemFirstView.of(
                      result,
                      findImageService.findFirstImageByAuctionItemId(result.getId()),
                      isPresent);
            })
            .collect(Collectors.toList());
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
  public CommentSearchResponse findGetLikeCommentByUserId(SearchByUserDto searchByUserDto, Long userId) {
    return commentLikeRepository.searchCommentLikePage(searchByUserDto, userId);
  }

  @Override
  public AuctionItemSearchResponse findBuyAuctionItemIdsByUserId(Long userId,Long page) {
    SearchItemDto searchItemDto =
        SearchItemDto.builder().user_id(userId).page(page).build();
    return auctionItemSearchService.findAuctionItemByRequest(searchItemDto);
  }
}
