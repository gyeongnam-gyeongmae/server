package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory;

import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionItemRepository
    extends JpaRepository<AuctionItem, Long>, AuctionItemRepositoryCustom {

  @Modifying
  @Query("update AuctionItem auction set auction.price = :price where auction.id = :id")
  int updateAuctionPrice(@Param("id") Long id, @Param("price") Long price);
}
