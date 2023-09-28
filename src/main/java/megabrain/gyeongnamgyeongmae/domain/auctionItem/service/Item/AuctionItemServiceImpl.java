package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item;

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
import megabrain.gyeongnamgyeongmae.domain.category.domain.entity.Category;
import megabrain.gyeongnamgyeongmae.domain.category.domain.repository.CategoryRepository;
import megabrain.gyeongnamgyeongmae.domain.category.service.CategoryService;
import megabrain.gyeongnamgyeongmae.domain.image.domain.entity.Image;
import megabrain.gyeongnamgyeongmae.domain.image.domain.repository.ImageRepository;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.domain.repository.UserRepository;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuctionItemServiceImpl implements AuctionItemService {

    private final AuctionItemRepository auctionItemRepository;
    private final AuctionItemLikeRepository auctionItemLikeRepository;
    private final CategoryService categoryService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final ImageRepository imageRepository;

    @Override
    @Transactional
    public void createAuctionItem(CreateAuctionItemRequest createAuctionItemRequest) {
        checkClosedTime(createAuctionItemRequest.getClosedTime());
        AuctionItem auctionItem = createAuctionItemRequest.toEntity();
        auctionItem.setUser(userService.findUserById(createAuctionItemRequest.getUserId()));
        auctionItem.setCategory(categoryService.findCategoryByName(createAuctionItemRequest.getCategory()));
        auctionItemRepository.save(auctionItem);
    }

    @Override
    @Transactional
    public AuctionItemResponse findAuctionItemById(Long id) {
        AuctionItem auctionItem =
                auctionItemRepository.findById(id).orElseThrow(AuctionNotFoundException::new);
        List<Image> images = imageRepository.findImageByAuctionItemId(id);
        auctionItem.checkShowAuctionItem(auctionItem);
        updateAuctionItemViewCount(auctionItem);
        AuctionItemResponse auctionItemResponse = AuctionItemResponse.of(auctionItem);
        if (!images.isEmpty()) {
            List<String> imageUrls = images.stream().map(this::makeImageUrl).collect(Collectors.toList());
            auctionItemResponse.setImages(imageUrls);
        }
        return auctionItemResponse;
    }

    private String makeImageUrl(Image image) {
        return image.getImageUrl();
    }

    @Override
    @Transactional
    public void updateAuctionItemViewCount(AuctionItem auctionItem) {
        auctionItem.plusViewCount();
    }

    @Override
    @Transactional
    public void updateAuctionItem(UpdateAuctionItemRequest upDateAuctionItemRequest, Long id) {
        checkClosedTime(upDateAuctionItemRequest.getClosedTime());
        Category categoryEntity =
                categoryService.findCategoryByName(upDateAuctionItemRequest.getCategory());
        // TODO: 유저 정보를 가져오는 부분을 리팩토링 해야함
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
