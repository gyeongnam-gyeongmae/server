package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.QAuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemFirstView;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemPaginationDto;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemSearchResponse;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;
import megabrain.gyeongnamgyeongmae.domain.category.domain.entity.QCategory;
import megabrain.gyeongnamgyeongmae.domain.image.domain.repository.ImageRepository;

public class AuctionItemRepositoryCustomImpl implements AuctionItemRepositoryCustom {

  public final JPAQueryFactory queryFactory;

  public final ImageRepository imageRepository;

  public AuctionItemRepositoryCustomImpl(EntityManager em, ImageRepository imageRepository) {
    this.queryFactory = new JPAQueryFactory(em);
    this.imageRepository = imageRepository;
  }

  public AuctionItemSearchResponse searchAuctionItemPage(
      SearchAuctionItemSortedRequest searchAuctionItemSortedRequest) {

    QAuctionItem auctionItem = QAuctionItem.auctionItem;
    QCategory category = QCategory.category;

    List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
    BooleanBuilder statusBuilder = new BooleanBuilder();
    BooleanBuilder sellBuilder = new BooleanBuilder();
    BooleanBuilder keywordStatus = new BooleanBuilder();
    BooleanBuilder categoryStatus = new BooleanBuilder();

    searchAuctionItemSortedRequest.applySearchCategory(categoryStatus, auctionItem);
    searchAuctionItemSortedRequest.applyKeyWordStatus(keywordStatus, auctionItem);
    searchAuctionItemSortedRequest.applySearchPrice(orderSpecifiers, auctionItem);
    searchAuctionItemSortedRequest.applySearchLike(orderSpecifiers, auctionItem);
    searchAuctionItemSortedRequest.applySearchTime(orderSpecifiers, auctionItem);
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
                keywordStatus,
                sellBuilder);

    Long page = searchAuctionItemSortedRequest.getPage();
    Long itemsPerPage = 10L;

    List<AuctionItem> results =
        query
            .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
            .offset((page - 1) * itemsPerPage)
            .limit(itemsPerPage)
            .fetch();

    AuctionItemPaginationDto paginationInfo = new AuctionItemPaginationDto();
    paginationInfo.setCurrentPage(page);
    paginationInfo.setItemCount((long) results.size());
    paginationInfo.setItemsPerPage(itemsPerPage);
    paginationInfo.setTotalItems((long) query.fetch().size());
    paginationInfo.setTotalPages(((long) query.fetch().size() + itemsPerPage - 1) / itemsPerPage);

    List<AuctionItemFirstView> auctionItemFirstViews =
        results.stream()
            .map(
                result ->
                    AuctionItemFirstView.of(
                        result, imageRepository.findFirstImageByAuctionItemId(result.getId())))
            .collect(Collectors.toList());

    AuctionItemSearchResponse auctionItemSearchResponse = new AuctionItemSearchResponse();
    auctionItemSearchResponse.setAuctionItemPaginationDto(paginationInfo);
    auctionItemSearchResponse.setAuctionItemFirstViewPage(auctionItemFirstViews);

    return auctionItemSearchResponse;
  }
}
