package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory;

import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.CommentSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserProfile.SearchByUserDto;

public interface CommentLikeRepositoryCustom {
    CommentSearchResponse searchCommentLikePage(Long userId);
}
