package megabrain.gyeongnamgyeongmae.auctionItem.domain.repostiory;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import megabrain.gyeongnamgyeongmae.Category.domain.entity.QCategory;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.QAuctionItem;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class AuctionItemRepositoryCustomImpl implements AuctionItemRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public AuctionItemRepositoryCustomImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public Page<AuctionItem> searchAuctionItemPage(
      SearchAuctionItemSortedRequest searchAuctionItemSortedRequest, Pageable pageable) {

    QAuctionItem auctionItem = QAuctionItem.auctionItem;
    QCategory category = QCategory.category;

    List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
    BooleanBuilder statusBuilder = new BooleanBuilder();
    BooleanBuilder sellBuilder = new BooleanBuilder();
    BooleanBuilder keywordStatus = new BooleanBuilder();
    BooleanBuilder categoryStatus = new BooleanBuilder();

    searchAuctionItemSortedRequest.applySearchCategory(categoryStatus, auctionItem);
    searchAuctionItemSortedRequest.applySearchStatus(statusBuilder, auctionItem);
    searchAuctionItemSortedRequest.applyKeyWordStatus(keywordStatus, auctionItem);
    searchAuctionItemSortedRequest.applySearchPrice(orderSpecifiers, auctionItem);
    searchAuctionItemSortedRequest.applySearchLike(orderSpecifiers, auctionItem);
    searchAuctionItemSortedRequest.applySearchView(orderSpecifiers, auctionItem);
    searchAuctionItemSortedRequest.applySearchTime(orderSpecifiers, auctionItem);
    searchAuctionItemSortedRequest.applySearchClosed(sellBuilder, auctionItem);

    JPAQuery<AuctionItem> query =
        queryFactory
            .selectFrom(auctionItem)
            .innerJoin(auctionItem.category, category)
            .where(
                auctionItem.removed.eq(false),
                categoryStatus,
                statusBuilder,
                sellBuilder,
                keywordStatus);

    List<AuctionItem> results =
        query
            .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    return new PageImpl<>(results, pageable, results.size());
  }
}
