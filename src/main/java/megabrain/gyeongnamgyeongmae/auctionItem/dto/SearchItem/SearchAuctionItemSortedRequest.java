package megabrain.gyeongnamgyeongmae.auctionItem.dto.SearchItem;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import java.util.List;
import lombok.*;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItemStatus;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionStatus;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.QAuctionItem;

@Data
public class SearchAuctionItemSortedRequest {

  private String category;

  private SearchStatus search_status = SearchStatus.ALL; // 신품 중고품 전체

  private Boolean closed = Boolean.FALSE; // 이미 경매가 끝낸 개시글도 보기

  private Boolean search_time = Boolean.FALSE; // 마감시간 근접

  private Boolean like = Boolean.FALSE; // 좋아요 정렬 기준

  private Boolean search_price = Boolean.FALSE; // 가격순 정렬

  private Boolean view_count = Boolean.FALSE; // 조회수 정렬 기준

  //  @NotNull private Location my_location; // 나의 현재 위치

  //  @NotNull private int km; // 몇키로 반경 게시글 조회

  //  @NotNull private int town; // 몇개의 마을 조회

  //  @NotNull private boolean comment; // 댓글수 정렬 기준

  //  @NotNull private String member; // 이름 검색

  //  @NotNull private boolean temperature; // 온도별 정렬 기준

  // 이 함수에 매개변수 인자로

  public void applySearchStatus(BooleanBuilder status, QAuctionItem item) {
    if (this.search_status == SearchStatus.NEW) {
      status.and(item.content.status.eq(AuctionItemStatus.NEW));
      return;
    }
    if (this.search_status == SearchStatus.USED) {
      status.and(item.content.status.eq(AuctionItemStatus.USED));
      return;
    }
    status.and(item.content.status.in(AuctionItemStatus.NEW, AuctionItemStatus.USED));
  }

  public void applySearchPrice(List<OrderSpecifier<?>> order, QAuctionItem item) {
    if (this.search_price) {
      order.add(item.price.desc());
      return;
    }
    order.add(item.price.asc());
  }

  public void applySearchLike(List<OrderSpecifier<?>> order, QAuctionItem item) {
    if (this.like) {
      order.add(item.like_count.desc());
      return;
    }
    order.add(item.like_count.asc());
  }

  public void applySearchView(List<OrderSpecifier<?>> order, QAuctionItem item) {
    if (this.view_count) {
      order.add(item.view_count.desc());
      return;
    }
    order.add(item.view_count.asc());
  }

  public void applySearchTime(List<OrderSpecifier<?>> order, QAuctionItem item) {
    if (this.search_time) {
      order.add(item.closedTime.desc());
      return;
    }
    order.add(item.closedTime.asc());
  }

  public void applySearchClosed(BooleanBuilder builder, QAuctionItem item) {
    if (this.closed) {
      builder.and(item.status.eq(AuctionStatus.ONGOING));
      return;
    }
    builder.and(item.status.in(AuctionStatus.ONGOING, AuctionStatus.CLOSED, AuctionStatus.BIDDING));
  }
}
