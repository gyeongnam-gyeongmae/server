package megabrain.gyeongnamgyeongmae.auctionItem.service.Item;

import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.Category.domain.repository.CategoryRepository;
import megabrain.gyeongnamgyeongmae.Category.service.CategoryService;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuctionItemService implements IAuctionItemService {

  private final AuctionItemRepository auctionItemRepository;
  private final CategoryService categoryService;
  private final MemberRepository memberRepository;
  private final CategoryRepository categoryRepository;

  @Override
  public void createAuctionItem(AuctionItem auctionItem) {
    auctionItemRepository.save(auctionItem);
  }

  @Override
  @Transactional(readOnly = true)
  public AuctionItem findAuctionItemById(Long id) {
    return auctionItemRepository.findById(id).orElseThrow(RuntimeException::new);
  }

  @Override
  public void updateAuctionItemViewCount(AuctionItem auctionItem) {
    auctionItem.setView_count(auctionItem.getView_count() + 1);
    auctionItemRepository.save(auctionItem);
  }

  @Override
  public void updateAuctionItem(AuctionItem auctionItem) {
    auctionItemRepository.save(auctionItem);
  }
}
