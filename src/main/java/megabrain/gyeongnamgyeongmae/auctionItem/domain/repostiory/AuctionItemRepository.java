package megabrain.gyeongnamgyeongmae.auctionItem.domain.repostiory;

import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuctionItemRepository extends JpaRepository<AuctionItem, Long> {

  //
  @Query(
      value =
          "SELECT auctionItem FROM AuctionItem auctionItem WHERE auctionItem.category.id = :category")
  Page<AuctionItem> searchAuctionItem(
      //      String searchStatus,
      //      boolean auctionStatus,
      //      String searchPrice,
      //      String searchTime,
      //      double latitude,
      //      double longitude,
      //      int km,
      Long category,
      //      boolean like,
      //      boolean comment,
      //      boolean temperature,
      Pageable pageable);
}
