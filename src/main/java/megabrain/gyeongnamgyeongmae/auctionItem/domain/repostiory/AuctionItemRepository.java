package megabrain.gyeongnamgyeongmae.auctionItem.domain.repostiory;

import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionItemRepository
    extends JpaRepository<AuctionItem, Long>, AuctionItemRepositoryCustom {}
