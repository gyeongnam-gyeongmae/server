package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionStatus;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.QAuctionItem;

@Data
public class SearchAuctionItemSortedRequest {

    private String keyword;

    private String category;

    @Schema(name = "city", example = "김해시", description = "유저 프로필 조회 시 반환되는 유저의 거주지역")
    private String city;

    @NotNull private Boolean closed;

    @NotNull private Boolean search_time;

    @NotNull private Boolean like;

    @NotNull private Boolean search_price;

    @NotNull private Long page;

  public SearchAuctionItemSortedRequest() {
    this.closed = Boolean.FALSE;
    this.search_time = Boolean.FALSE;
    this.search_price = Boolean.FALSE;
  }

  public void applyKeyWordStatus(BooleanBuilder status, QAuctionItem item) {
    if (this.keyword != null) {
      status.and(item.name.like("%" + this.keyword + "%"));
    }
  }

  public void applyUserCity(BooleanBuilder status, QAuctionItem item) {
    if (this.city != null) {
      status.and(item.user.address.city.eq(this.city));
    }
  }

  public void applySearchCategory(BooleanBuilder status, QAuctionItem item) {
    if (this.category != null) {
      status.and(item.category.name.eq(this.category));
    }
  }

  public void applySearchPrice(List<OrderSpecifier<?>> order, QAuctionItem item) {
    if (this.search_price) {
      order.add(item.price.asc());
    } else {
      order.add(item.price.desc());
    }
  }

  public void applySearchLike(List<OrderSpecifier<?>> order, QAuctionItem item) {
    if (this.like) {
      order.add(item.like_count.desc());
    } else {
      order.add(item.like_count.asc());
    }
  }

  public void applySearchTime(List<OrderSpecifier<?>> order, QAuctionItem item) {
    if (this.search_time) {
      order.add(item.createdAt.desc());
    } else {
      order.add(item.closedTime.desc());
    }
  }

  public void applySearchClosed(BooleanBuilder builder, QAuctionItem item) {
    if (this.closed) {
      builder.and(item.status.eq(AuctionStatus.ONGOING));
    } else {
      builder.and(
          item.status.in(AuctionStatus.ONGOING, AuctionStatus.CLOSED, AuctionStatus.BIDDING));
    }
  }
}
