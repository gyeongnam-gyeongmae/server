package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLike;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLikePK;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemLikeRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemLikeRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.CreateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.UpdateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.exception.AuctionNotFoundException;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.exception.AuctionTimeException;
import megabrain.gyeongnamgyeongmae.domain.category.service.CategoryService;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuctionItemServiceImpl implements AuctionItemService {

  private final AuctionItemRepository auctionItemRepository;
  private final AuctionItemLikeRepository auctionItemLikeRepository;
  private final CategoryService categoryService;
  private final UserService userService;

  @Override
  @Transactional
  public void createAuctionItem(CreateAuctionItemRequest createAuctionItemRequest) {
    checkClosedTime(createAuctionItemRequest.getClosedTime());
    AuctionItem auctionItem = createAuctionItemRequest.toEntity();
    auctionItem.setUser(userService.findUserById(createAuctionItemRequest.getUserId()));
    auctionItem.setCategory(
        categoryService.findCategoryByName(createAuctionItemRequest.getCategory()));
    auctionItemRepository.save(auctionItem);
  }

  @Override
  @Transactional(readOnly = true)
  public AuctionItem findAuctionItemById(Long id) {
    AuctionItem auctionItem =
        auctionItemRepository
            .findById(id)
            .orElseThrow(() -> new AuctionNotFoundException("경매품을 찾을 수 없습니다."));
    auctionItem.checkShowAuctionItem(auctionItem);
    return auctionItem;
  }

  @Override
  public AuctionItemResponse auctionItemResponse(AuctionItem auctionItem, List<String> imageUrls) {
    auctionItem.plusViewCount();
    return AuctionItemResponse.of(auctionItem, imageUrls);
  }

  @Override
  @Transactional
  public void updateAuctionItem(UpdateAuctionItemRequest upDateAuctionItemRequest, Long id) {
    checkClosedTime(upDateAuctionItemRequest.getClosedTime());
    AuctionItem auctionItem = findAuctionItemById(id);
    auctionItem.checkShowAuctionItem(auctionItem);
    auctionItem.setUser(userService.findUserById(upDateAuctionItemRequest.getUserId()));
    auctionItem.setCategory(
        categoryService.findCategoryByName(upDateAuctionItemRequest.getCategory()));
    auctionItem.updateAuctionItem(upDateAuctionItemRequest);
    auctionItemRepository.save(auctionItem);
  }

  private void checkClosedTime(LocalDateTime closedTime) {
    if (closedTime.isBefore(LocalDateTime.now().plusHours(24))) {
      throw new AuctionTimeException("경매 종료 시간은 24시간 이후여야 합니다.");
    }
  }

  @Override
  @Transactional
  public void deleteAuctionItemById(Long id) {
    AuctionItem auctionItem = findAuctionItemById(id);
    auctionItem.checkShowAuctionItem(auctionItem);
    auctionItem.removeAuctionItem(auctionItem);
    auctionItemRepository.save(auctionItem);
  }

  @Override
  @Transactional
  public void likeAuctionItemById(Long id, AuctionItemLikeRequest auctionItemLikeRequest) {
    AuctionItem auctionItem = findAuctionItemById(id);
    User user = userService.findUserById(auctionItemLikeRequest.getUserId());

    AuctionItemLikePK auctionItemLikePK =
        new AuctionItemLikePK(id, auctionItemLikeRequest.getUserId());

    AuctionItemLike auctionItemLike = auctionItemLike(auctionItemLikePK);

    if (auctionItemLike != null) {
      deleteAuctionItemLike(auctionItemLike);
      auctionItem.minusLikeCount();
    } else {
      auctionItemLike = createAndSaveAuctionItemLike(auctionItem, user, auctionItemLikePK);
      auctionItem.plusLikeCount();
      saveAuctionItemLike(auctionItemLike);
    }
    auctionItemRepository.save(auctionItem);
  }

  private AuctionItemLike createAndSaveAuctionItemLike(
      AuctionItem auctionItem, User user, AuctionItemLikePK auctionItemLikePK) {
    AuctionItemLike auctionItemLike =
        AuctionItemLike.builder().id(auctionItemLikePK).auctionItem(auctionItem).user(user).build();
    saveAuctionItemLike(auctionItemLike);
    return auctionItemLike;
  }

  private void saveAuctionItemLike(AuctionItemLike auctionItemLike) {
    auctionItemLikeRepository.save(auctionItemLike);
  }

  private void deleteAuctionItemLike(AuctionItemLike auctionItemLike) {
    auctionItemLikeRepository.delete(auctionItemLike);
  }

  private AuctionItemLike auctionItemLike(AuctionItemLikePK auctionItemLikePK) {
    return auctionItemLikeRepository.findById(auctionItemLikePK).orElse(null);
  }
}
