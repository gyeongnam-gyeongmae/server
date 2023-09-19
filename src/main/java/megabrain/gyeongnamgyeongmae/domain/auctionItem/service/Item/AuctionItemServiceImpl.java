package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.CreateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.UpdateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.domain.category.domain.entity.Category;
import megabrain.gyeongnamgyeongmae.domain.category.domain.repository.CategoryRepository;
import megabrain.gyeongnamgyeongmae.domain.category.service.CategoryService;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.domain.repository.UserRepository;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuctionItemServiceImpl implements AuctionItemService {

  private final AuctionItemRepository auctionItemRepository;
  private final CategoryService categoryService;
  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;
  private final UserService userService;

  @Override
  @Transactional
  public void createAuctionItem(CreateAuctionItemRequest createAuctionItemRequest) {
    checkClosedTime(createAuctionItemRequest.getClosedTime());
    User userEntity =
        userRepository
            .findById(createAuctionItemRequest.getUserId())
            .orElseThrow(RuntimeException::new);
    Category categoryEntity =
        categoryService.findCategoryByName(createAuctionItemRequest.getCategory());
    AuctionItem auctionItem = createAuctionItemRequest.toEntity();
    auctionItem.setUser(userEntity);
    auctionItem.setCategory(categoryEntity);
    auctionItemRepository.save(auctionItem);
  }

  @Override
  @Transactional
  public AuctionItemResponse findAuctionItemById(Long id) {
    AuctionItem auctionItem = auctionItemRepository.findById(id).orElseThrow(RuntimeException::new);
    auctionItem.checkShowAuctionItem(auctionItem);
    updateAuctionItemViewCount(auctionItem);
    return AuctionItemResponse.of(auctionItem);
  }

  @Override
  public void updateAuctionItemViewCount(AuctionItem auctionItem) {
    auctionItem.setView_count(auctionItem.getView_count() + 1);
    auctionItemRepository.save(auctionItem);
  }

  @Override
  @Transactional
  public void updateAuctionItem(UpdateAuctionItemRequest upDateAuctionItemRequest, Long id) {
    checkClosedTime(upDateAuctionItemRequest.getClosedTime());
    Category categoryEntity =
        categoryService.findCategoryByName(upDateAuctionItemRequest.getCategory());
    AuctionItem auctionItem = auctionItemRepository.findById(id).orElseThrow(RuntimeException::new);

    if (!(Objects.equals(auctionItem.getUser().getId(), upDateAuctionItemRequest.getUserId()))) {
      throw new RuntimeException("잘못된 회원입니다 ");
    }

    auctionItem.checkShowAuctionItem(auctionItem);
    auctionItem.updateAuctionItem(upDateAuctionItemRequest);
    auctionItem.setCategory(categoryEntity);
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
}
