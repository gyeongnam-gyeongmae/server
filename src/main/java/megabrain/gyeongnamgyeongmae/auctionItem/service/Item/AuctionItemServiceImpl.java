package megabrain.gyeongnamgyeongmae.auctionItem.service.Item;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.Category.domain.entity.Category;
import megabrain.gyeongnamgyeongmae.Category.domain.repository.CategoryRepository;
import megabrain.gyeongnamgyeongmae.Category.service.CategoryService;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.AuctionItemResponse;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.CreateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.UpdateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.member.domain.entity.Member;
import megabrain.gyeongnamgyeongmae.member.domain.repository.MemberRepository;
import megabrain.gyeongnamgyeongmae.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuctionItemServiceImpl implements AuctionItemService {

  private final AuctionItemRepository auctionItemRepository;
  private final CategoryService categoryService;
  private final MemberRepository memberRepository;
  private final CategoryRepository categoryRepository;
  private final MemberService memberService;

  @Override
  @Transactional
  public void createAuctionItem(CreateAuctionItemRequest createAuctionItemRequest) {
    checkClosedTime(createAuctionItemRequest.getClosedTime());
    Member memberEntity = memberService.findMemberById(createAuctionItemRequest.getMember());
    Category categoryEntity =
        categoryService.findCategoryByName(createAuctionItemRequest.getCategory());
    AuctionItem auctionItem = createAuctionItemRequest.toEntity();
    auctionItem.setMember(memberEntity);
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
