package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory;

import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLike;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLikePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionItemLikeRepository
    extends JpaRepository<AuctionItemLike, AuctionItemLikePK> {

  @Query("SELECT ail FROM AuctionItemLike ail WHERE ail.user.id = :userId")
  List<AuctionItemLike> AuctionLikeFindByUserId(@Param("userId") Long userId);

//  @Query(
//      "SELECT a FROM AuctionItemLike a WHERE a.auctionItem.id = :auctionItemId AND a.user.id = :userId")
//  AuctionItemLike AuctionLikeFindByAuctionItemIdAndUserId(
//      @Param("auctionItemId") Long auctionItemId, @Param("userId") Long userId);
}
