package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionStatus;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.QAuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;

@Data
public class SearchItemDto {

  private String keyword;

  private String category;

  private String city;

  private Long user_id;

  private Boolean closed;

  private Boolean search_time;

  private Boolean like;

  private Boolean search_price;

  private Long page;

  @Builder
  public SearchItemDto(Long user_id, Long page) {
    this.user_id = user_id;
    this.page = page;
    search_time = false;
    closed = false;
    like = false;
    search_price = false;
  }

  public static SearchItemDto of(SearchAuctionItemSortedRequest request) {
    SearchItemDto dto = SearchItemDto.builder().build();
    dto.setKeyword(request.getKeyword());
    dto.setCategory(request.getCategory());
    dto.setCity(request.getCity());
    dto.setClosed(request.getClosed());
    dto.setSearch_time(request.getSearch_time());
    dto.setLike(request.getLike());
    dto.setSearch_price(request.getSearch_price());
    dto.setPage(request.getPage());
    return dto;
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

  public void applySearchUser(BooleanBuilder builder, QAuctionItem item) {
    if (this.closed) {
      builder.and(item.user.id.eq(this.page));
    }
  }
}
