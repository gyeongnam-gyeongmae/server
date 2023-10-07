package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLike;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLikePK;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionStatus;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemLikeRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemLikeRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.CreateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.UpdateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.exception.AuctionNotFoundException;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.exception.AuctionTimeException;
import megabrain.gyeongnamgyeongmae.domain.category.service.CategoryServiceInterface;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserServiceInterface;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuctionItemServiceImpl implements AuctionItemService {

  private final AuctionItemRepository auctionItemRepository;
  private final AuctionItemLikeRepository auctionItemLikeRepository;
  private final CategoryServiceInterface categoryService;
  private final UserServiceInterface userService;

  @Override
  @Transactional
  public AuctionItem createAuctionItem(CreateAuctionItemRequest createAuctionItemRequest) {
    checkClosedTime(createAuctionItemRequest.getClosedTime());
    AuctionItem auctionItem = createAuctionItemRequest.toEntity();
    setAuctionItemProperties(
        auctionItem, createAuctionItemRequest.getUserId(), createAuctionItemRequest.getCategory());
    auctionItemRepository.save(auctionItem);
    return auctionItem;
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
  public void saveAuctionItem(AuctionItem auctionItem) {
    auctionItemRepository.save(auctionItem);
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
    sellerCheck(auctionItem, upDateAuctionItemRequest.getUserId());
    setAuctionItemProperties(
        auctionItem, upDateAuctionItemRequest.getUserId(), upDateAuctionItemRequest.getCategory());
    auctionItem.updateAuctionItem(upDateAuctionItemRequest);
    auctionItemRepository.save(auctionItem);
  }

  @Override
  public Boolean updateAuctionPrice(Long id, Long price) {
    return auctionItemRepository.updateAuctionPrice(id, price) == 1;
  }

  private void sellerCheck(AuctionItem auctionItem, Long userId) {
    if (!auctionItem.getUser().getId().equals(userId)) {
      throw new AuctionNotFoundException("작성자만 수정할 수 있습니다.");
    }
  }

  private void setAuctionItemProperties(AuctionItem auctionItem, Long userId, String categoryName) {
    auctionItem.setUser(userService.findUserById(userId));
    auctionItem.setCategory(categoryService.findCategoryByName(categoryName));
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

  @Override
  public List<AuctionItemLike> auctionItemLikesFindByUserId(Long id) {
    return auctionItemLikeRepository.AuctionLikeFindByUserId(id);
  }

  @Transactional
  @Scheduled(fixedRate = 60000)
  public void updateOngoingToBidding() {
    LocalDateTime now = LocalDateTime.now();
    List<AuctionItem> ongoingItems = auctionItemRepository.findByStatusAndClosedTimeBefore(AuctionStatus.ONGOING, now);

    for (AuctionItem item : ongoingItems) {
      item.setStatus(AuctionStatus.BIDDING);
    }

    auctionItemRepository.saveAll(ongoingItems);
  }
}
