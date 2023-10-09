package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Comment;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.CommentLike;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.QComment;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.QCommentLike;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.CommentFirstView;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment.CommentSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemPaginationDto;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.QUser;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserProfile.SearchByUserDto;

@RequiredArgsConstructor
public class CommentLikeRepositoryCustomImpl implements CommentLikeRepositoryCustom {

  public final JPAQueryFactory queryFactory;
  public final AuctionItemCommentRepository commentRepository;

  public CommentSearchResponse searchCommentLikePage(SearchByUserDto searchByUserDto,Long userId) {

    QComment comment = QComment.comment;
    QCommentLike commentLike = QCommentLike.commentLike;
    QUser user = QUser.user;

    List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
    orderSpecifiers.add(commentLike.createdAt.desc());

    if (searchByUserDto.isClosed()) {
      orderSpecifiers.add(commentLike.createdAt.asc());
    } else {
      orderSpecifiers.add(commentLike.createdAt.desc());
    }

    JPAQuery<CommentLike> query =
            queryFactory
                    .selectFrom(commentLike)
                    .innerJoin(commentLike.user, user)
                    .innerJoin(commentLike.comment, comment)
                    .where(commentLike.user.id.eq(userId));

    Long page = searchByUserDto.getPage();
    Long itemsPerPage = 10L;

    List<CommentLike> results =
        query
            .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
            .offset((page - 1) * itemsPerPage)
            .limit(itemsPerPage)
            .fetch();

    Long totalItems = query.fetchCount();

    AuctionItemPaginationDto paginationInfo = new AuctionItemPaginationDto();
    paginationInfo.setCurrentPage(page);
    paginationInfo.setItemCount((long) results.size());
    paginationInfo.setItemsPerPage(itemsPerPage);
    paginationInfo.setTotalItems(totalItems);
    paginationInfo.setTotalPages((totalItems + itemsPerPage - 1) / itemsPerPage);

    List<CommentFirstView> firstViews = new ArrayList<>();
    for (CommentLike cl : results) {
      commentRepository.findById(cl.getComment().getId()).ifPresent(a -> firstViews.add(new CommentFirstView(a)));
    }
    return CommentSearchResponse.builder()
            .commentFirstViewPage(firstViews)
            .auctionItemPaginationDto(paginationInfo)
            .build();
    }
}
