package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Search;

import java.awt.print.Pageable;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.domain.member.domain.entity.Member;
import org.springframework.data.domain.Page;

public interface IAuctionItemSearchService {

  public AuctionItemRepository findAllByMemberAddress(Member member, Pageable pageable);

  public AuctionItemRepository findAllByCategory(String category, Pageable pageable);

  public Page<AuctionItem> findAllByTimeLine(Pageable pageable);
}
