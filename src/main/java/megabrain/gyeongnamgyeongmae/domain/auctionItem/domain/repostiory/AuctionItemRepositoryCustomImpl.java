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
import megabrain.gyeongnamgyeongmae.domain.image.Service.FindImageServiceInterface;

@RequiredArgsConstructor
public class AuctionItemRepositoryCustomImpl implements AuctionItemRepositoryCustom {

  public final JPAQueryFactory queryFactory;

  public final FindImageServiceInterface findImageService;

  public AuctionItemSearchResponse searchAuctionItemPage(
      SearchAuctionItemSortedRequest searchAuctionItemSortedRequest) {

    QAuctionItem auctionItem = QAuctionItem.auctionItem;
    QCategory category = QCategory.category;

    List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
    BooleanBuilder keywordStatus = new BooleanBuilder();
    BooleanBuilder categoryStatus = new BooleanBuilder();
    BooleanBuilder sellStatus = new BooleanBuilder();

    searchAuctionItemSortedRequest.applySearchPrice(orderSpecifiers, auctionItem);
    searchAuctionItemSortedRequest.applySearchLike(orderSpecifiers, auctionItem);
    searchAuctionItemSortedRequest.applySearchTime(orderSpecifiers, auctionItem);

    searchAuctionItemSortedRequest.applySearchCategory(categoryStatus, auctionItem);
    searchAuctionItemSortedRequest.applyKeyWordStatus(keywordStatus, auctionItem);
    searchAuctionItemSortedRequest.applySearchClosed(sellStatus, auctionItem);

    JPAQuery<AuctionItem> query =
        queryFactory
            .selectFrom(auctionItem)
            .innerJoin(auctionItem.category, category)
            .where(auctionItem.removed.eq(false), categoryStatus, keywordStatus, sellStatus);

    Long page = searchAuctionItemSortedRequest.getPage();
    Long itemsPerPage = 10L;

    List<AuctionItem> results =
        query
            .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
            .offset((page - 1) * itemsPerPage)
            .limit(itemsPerPage)
            .fetch();

    long totalItems = query.fetch().size();

    AuctionItemPaginationDto paginationInfo = new AuctionItemPaginationDto();
    paginationInfo.setCurrentPage(page);
    paginationInfo.setItemCount((long) results.size());
    paginationInfo.setItemsPerPage(itemsPerPage);
    paginationInfo.setTotalItems(totalItems);
    paginationInfo.setTotalPages((totalItems + itemsPerPage - 1) / itemsPerPage);

    List<AuctionItemFirstView> auctionItemFirstViews = convertResultsToViews(results);

    return AuctionItemSearchResponse.builder()
        .auctionItemFirstViewPage(auctionItemFirstViews)
        .auctionItemPaginationDto(paginationInfo)
        .build();
  }

  private List<AuctionItemFirstView> convertResultsToViews(List<AuctionItem> results) {
    return results.stream()
        .map(
            result ->
                AuctionItemFirstView.of(
                    result, findImageService.findFirstImageByAuctionItemId(result.getId())))
        .collect(Collectors.toList());
  }
}
