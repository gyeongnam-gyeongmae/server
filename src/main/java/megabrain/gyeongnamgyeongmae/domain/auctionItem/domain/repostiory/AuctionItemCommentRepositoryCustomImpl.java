package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;

import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Comment;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.QAuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.QComment;

public class AuctionItemCommentRepositoryCustomImpl implements AuctionItemCommentRepositoryCustom {
  private final JPAQueryFactory queryFactory;

  public AuctionItemCommentRepositoryCustomImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  public List<Comment> findByAuctionItemCommentByAuctionId(Long id) {
    QAuctionItem auctionItem = QAuctionItem.auctionItem;
    QComment comment = QComment.comment;

    BooleanBuilder whereClause = new BooleanBuilder();
    whereClause
        .and(comment.auctionItem.id.eq(id))
        .and(comment.parent.isNull())
        .and(comment.removed.eq(false));

    JPAQuery<Comment> query =
        queryFactory
            .select(comment)
            .from(comment)
            .where(whereClause)
            .orderBy(comment.createdAt.asc());

    return query.fetch();
  }
}
