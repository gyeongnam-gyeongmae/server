package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionStatus;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.QAuctionItem;

@Data
public class SearchAuctionItemSortedRequest {
  // TODO: dto 수정 요구

  private String keyword;

  private String category;

  @NotEmpty private Boolean closed;

  @NotEmpty private Boolean search_time;

  @NotEmpty private Boolean like;

  @NotEmpty private Boolean search_price;

  @NotEmpty private Long page;

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

  //    public void applySearchView(List<OrderSpecifier<?>> order, QAuctionItem item) {
  //        if (this.view_count) {
  //            order.add(item.view_count.desc());
  //        } else {
  //            order.add(item.view_count.asc());
  //        }
  //    }

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
