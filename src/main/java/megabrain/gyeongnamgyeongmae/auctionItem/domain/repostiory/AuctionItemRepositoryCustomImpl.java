package megabrain.gyeongnamgyeongmae.auctionItem.domain.repostiory;

import static megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.QAuctionItem.auctionItem;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import megabrain.gyeongnamgyeongmae.Category.domain.entity.QCategory;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItemStatus;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.QAuctionItem;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.SearchItem.SearchStatus;
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
    BooleanBuilder builder = new BooleanBuilder();

    if (SearchStatus.NEW.equals(searchAuctionItemSortedRequest.getSearch_status())) {
      builder.and(auctionItem.content.status.eq(AuctionItemStatus.NEW));
    }
    if (SearchStatus.USED.equals(searchAuctionItemSortedRequest.getSearch_status())) {
      builder.and(auctionItem.content.status.eq(AuctionItemStatus.USED));
    }
    if (SearchStatus.ALL.equals(searchAuctionItemSortedRequest.getSearch_status())) {
      builder.and(auctionItem.content.status.in(AuctionItemStatus.NEW, AuctionItemStatus.USED));
    }

    if (searchAuctionItemSortedRequest.isSearch_price()) {
      orderSpecifiers.add(auctionItem.price.desc());
    }
    if (!searchAuctionItemSortedRequest.isSearch_price()) {
      orderSpecifiers.add(auctionItem.price.asc());
    }
    if (searchAuctionItemSortedRequest.isLike()) {
      orderSpecifiers.add(auctionItem.like_count.desc());
    }
    if (!searchAuctionItemSortedRequest.isLike()) {
      orderSpecifiers.add(auctionItem.like_count.asc());
    }
    if (searchAuctionItemSortedRequest.isView_count()) {
      orderSpecifiers.add(auctionItem.view_count.desc());
    }
    if (!searchAuctionItemSortedRequest.isView_count()) {
      orderSpecifiers.add(auctionItem.view_count.asc());
    }

    JPAQuery<AuctionItem> query =
        queryFactory
            .selectFrom(auctionItem)
            .innerJoin(auctionItem.category, category)
            .where(category.name.eq(searchAuctionItemSortedRequest.getCategory()), builder);

    List<AuctionItem> results =
        query
            .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    return new PageImpl<>(results, pageable, results.size());
  }
}

//    SearchAuctionItemSortedRequest.Sort a = searchAuctionItemSortedRequest.getSort();
//    List<AuctionItem> results =
//        queryFactory
//            .selectFrom(auctionItem)
//            .innerJoin(auctionItem.category, category)
//            .where(category.name.eq(searchAuctionItemSortedRequest.getCategory()))
//            .orderBy(sort)
//            .offset(pageable.getOffset())
//            .limit(pageable.getPageSize())
//            .fetch();

//  .orderBy(auctionItem.like_count.asc())
