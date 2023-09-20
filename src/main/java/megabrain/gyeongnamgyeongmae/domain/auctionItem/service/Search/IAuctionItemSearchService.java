package megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Search;

import java.awt.print.Pageable;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import org.springframework.data.domain.Page;

public interface IAuctionItemSearchService {

  public AuctionItemRepository findAllByUserAddress(User user, Pageable pageable);

  public AuctionItemRepository findAllByCategory(String category, Pageable pageable);

  public Page<AuctionItem> findAllByTimeLine(Pageable pageable);
}
