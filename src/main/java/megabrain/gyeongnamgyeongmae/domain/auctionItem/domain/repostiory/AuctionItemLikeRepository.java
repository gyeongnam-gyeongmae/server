package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory;

import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLike;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemLikePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionItemLikeRepository extends JpaRepository<AuctionItemLike, AuctionItemLikePK> {

}
