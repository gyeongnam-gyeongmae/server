package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory;

import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Comment;

import java.util.List;

public interface AuctionItemCommentRepositoryCustom {
    List<Comment> findByAuctionItemCommentByAuctionId(Long id);
}
