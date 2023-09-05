package megabrain.gyeongnamgyeongmae.auctionItem.domain.repostiory;

import static megabrain.gyeongnamgyeongmae.Category.domain.entity.QCategory.category;
import static megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.QAuctionItem.auctionItem;

import com.querydsl.jpa.impl.JPAQueryFactory;
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

    List<AuctionItem> results =
        queryFactory
            .selectFrom(auctionItem)
            .innerJoin(auctionItem.category, category)
            .where(category.name.eq(searchAuctionItemSortedRequest.getCategory()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    return new PageImpl<>(results, pageable, results.size());
  }
}
