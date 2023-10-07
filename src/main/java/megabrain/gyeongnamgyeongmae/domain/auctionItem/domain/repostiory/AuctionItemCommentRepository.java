package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory;

import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionItemCommentRepository
    extends JpaRepository<Comment, Long>{}
