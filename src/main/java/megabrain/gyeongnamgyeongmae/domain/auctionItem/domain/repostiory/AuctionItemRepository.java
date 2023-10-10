package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory;

import java.time.LocalDateTime;
import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionItemRepository
    extends JpaRepository<AuctionItem, Long>, AuctionItemRepositoryCustom {

  List<AuctionItem> findByStatusAndClosedTimeBefore(AuctionStatus status, LocalDateTime beforeTime);

  @Modifying
  @Query("update AuctionItem auction set auction.nowPrice = :price where auction.id = :id")
  int updateAuctionPrice(@Param("id") Long id, @Param("price") Long price);
}
