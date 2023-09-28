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
import megabrain.gyeongnamgyeongmae.domain.category.service.CategoryService;
import megabrain.gyeongnamgyeongmae.domain.image.domain.repository.ImageRepository;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.domain.repository.UserRepository;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuctionItemServiceImpl implements AuctionItemService {

  private final AuctionItemRepository auctionItemRepository;
  private final AuctionItemLikeRepository auctionItemLikeRepository;
  private final CategoryService categoryService;
  private final UserRepository userRepository;
  private final UserService userService;
  private final ImageRepository imageRepository;

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
        auctionItemRepository.findById(id).orElseThrow(AuctionNotFoundException::new);
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

  @Override
  public void checkClosedTime(LocalDateTime closedTime) {
    LocalDateTime now = LocalDateTime.now();
    if (closedTime.isBefore(now.plusHours(24))) {
      throw new RuntimeException("경매 종료 시간은 현재 시간보다 24시간 이후여야 합니다.");
    }
  }

  @Override
  @Transactional
  public void deleteAuctionItemById(Long id) {
    AuctionItem auctionItem = auctionItemRepository.findById(id).orElseThrow(RuntimeException::new);
    auctionItem.removeAuctionItem(auctionItem);
    auctionItemRepository.save(auctionItem);
  }

  @Override
  @Transactional
  public void likeAuctionItemById(Long id, AuctionItemLikeRequest auctionItemLikeRequest) {
    AuctionItem auctionItem = auctionItemRepository.findById(id).orElseThrow(RuntimeException::new);
    User user =
        userRepository
            .findById(auctionItemLikeRequest.getUserId())
            .orElseThrow(RuntimeException::new);

    AuctionItemLikePK auctionItemLikePK = new AuctionItemLikePK();
    auctionItemLikePK.setAuctionItemId(id);
    auctionItemLikePK.setUserId(auctionItemLikeRequest.getUserId());

    AuctionItemLike auctionItemLike =
        auctionItemLikeRepository.findById(auctionItemLikePK).orElse(null);

    if (auctionItemLike != null) {
      auctionItemLikeRepository.delete(auctionItemLike);
      auctionItem.minusLikeCount();
      auctionItemRepository.save(auctionItem);
    } else {
      auctionItemLike =
          AuctionItemLike.builder()
              .id(auctionItemLikePK)
              .auctionItem(auctionItem)
              .user(user)
              .build();
      auctionItemLikeRepository.save(auctionItemLike);
      auctionItem.plusLikeCount();
      auctionItemRepository.save(auctionItem);
    }
  }
}
