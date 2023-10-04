package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory;

import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionBid;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionBidPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionBidRepository extends JpaRepository<AuctionBid, AuctionBidPK> {}
