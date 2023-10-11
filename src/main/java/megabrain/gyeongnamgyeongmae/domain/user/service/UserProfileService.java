package megabrain.gyeongnamgyeongmae.domain.user.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLike;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLikePK;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemLikeRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.CommentLikeRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemFirstView;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.CommentSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemPaginationDto;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItemDto;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Comment.AuctionItemCommentService;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item.AuctionItemService;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Search.AuctionItemSearchService;
import megabrain.gyeongnamgyeongmae.domain.authentication.dto.UserProfileResponse;
import megabrain.gyeongnamgyeongmae.domain.image.Service.FindImageServiceInterface;
import megabrain.gyeongnamgyeongmae.domain.image.domain.entity.Image;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserItemSearchDto;
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
  private final AuctionItemLikeRepository auctionItemLikeRepository;

  @Override
  public AuctionItemSearchResponse findLikedAuctionItemIdsByUserId(Long userId, Long page) {

    Long itemsPerPage = 10L;
    List<AuctionItemLike> auctionItemLikes =
        auctionItemService.auctionItemLikesFindByUserId(userId);
    List<AuctionItem> auctionItems = auctionItemService.auctionItemFindByIds(auctionItemLikes);

    //    return auctionItems.stream()
    //            .map(AuctionItem::getId)
    //            .collect(Collectors.toList());

    Long totalItems = (long) auctionItems.size();

    Long startIndex = (page - 1) * itemsPerPage;
    Long endIndex = Math.min(startIndex + itemsPerPage, totalItems);

    List<AuctionItem> currentPageAuctionItems =
        auctionItems.subList(startIndex.intValue(), endIndex.intValue());
    List<AuctionItemFirstView> firstViews = convertResultsToViews(currentPageAuctionItems, userId);

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

  private List<AuctionItemFirstView> convertResultsToViews(
      List<AuctionItem> results, Long searcherId) {
    return results.stream()
        .map(
            result -> {
              boolean isPresent =
                  auctionItemLikeRepository
                      .findById(new AuctionItemLikePK(result.getId(), searcherId))
                      .isPresent();
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
    request.setSearcherId(userId);

    return auctionItemSearchService.findAuctionItemByRequest(request);
  }

  @Override
  @Transactional(readOnly = true)
  public CommentSearchResponse findGetLikeCommentByUserId(Long userId, Long page) {
    return commentLikeRepository.searchCommentLikePage(userId, page);
  }

  @Override
  public AuctionItemSearchResponse findBuyAuctionItemIdsByUserId(Long userId, Long page) {
    SearchItemDto searchItemDto = SearchItemDto.builder().user_id(userId).page(page).build();
    searchItemDto.setBuyUserId(userId);
    searchItemDto.setSearcherId(userId);
    return auctionItemSearchService.findAuctionItemByRequest(searchItemDto);
  }

  @Override
  public UserProfileResponse getUserProfile(User user) {
    Image image = findImageService.findFirstImageByUserId(user.getId());
    UserProfileResponse userProfile = UserProfileResponse.of(user);
    if (image != null) {
      userProfile.setNewImageUrl(image.getImageUrl());
    }
    return userProfile;
  }
}
