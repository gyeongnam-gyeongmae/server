package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionStatus;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.QAuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.SearchAuctionItemSortedRequest;
import megabrain.gyeongnamgyeongmae.domain.user.dto.FindStatus;

@Data
public class SearchItemDto {

  private String keyword;

  private String category;

  private String city;

  private Long user_id;

  private Boolean search_time;

  private Boolean like;

  private Boolean search_price;

  private FindStatus onlyOpenOrClosed;

  private Long BuyUserId;

  private Boolean basic;

  private Long page;

  private Long SearcherId;

  @Builder
  public SearchItemDto(Long user_id, Long page, FindStatus closed) {
    this.user_id = user_id;
    this.page = page;
    this.onlyOpenOrClosed = closed;
    search_time = null;
    like = null;
    search_price = null;
    basic = true;
  }

  public void setBuyUserId(Long buyUserId) {
    this.BuyUserId = buyUserId;
    this.user_id = null;
  }

  public void setSearcherId(Long searcherId) {
    this.SearcherId = searcherId;
  }

  public static SearchItemDto of(SearchAuctionItemSortedRequest request) {
    SearchItemDto dto = SearchItemDto.builder().build();
    dto.setKeyword(request.getKeyword());
    dto.setCategory(request.getCategory());
    dto.setCity(request.getCity());
    dto.setSearch_time(request.getSearch_time());
    dto.setLike(request.getLike());
    dto.setSearch_price(request.getSearch_price());
    dto.setPage(request.getPage());
    dto.setOnlyOpenOrClosed(request.getClosed());
    dto.setBasic(request.getBasic());
    dto.setSearcherId(request.getSearcherId());
    return dto;
  }

  public void applySearchOnly(BooleanBuilder onlyStatus, QAuctionItem auctionItem) {
    if (this.onlyOpenOrClosed == FindStatus.ALL) {
      onlyStatus.and(
          auctionItem.status.in(
              AuctionStatus.ONGOING, AuctionStatus.CLOSED, AuctionStatus.BIDDING));
    }
    if (this.onlyOpenOrClosed == FindStatus.OPEN) {
      onlyStatus.and(auctionItem.status.eq(AuctionStatus.ONGOING));
    }
    if (this.onlyOpenOrClosed == FindStatus.CLOSED) {
      onlyStatus.and(auctionItem.status.in(AuctionStatus.CLOSED, AuctionStatus.BIDDING));
    }
  }

  public void applySearchBuyer(BooleanBuilder buyerStatus, QAuctionItem item) {
    if (this.BuyUserId != null) {
      buyerStatus.and(item.buyer.id.eq(this.BuyUserId));
    }
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
    if (order == null) {
      return;
    }
    if (this.search_time) {
      order.add(item.createdAt.desc());
    } else {
      order.add(item.closedTime.desc());
    }
  }

  public void applySearchUser(BooleanBuilder builder, QAuctionItem item) {
    if (this.user_id != null) {
      builder.and(item.user.id.eq(this.user_id));
    }
  }

  public void applyBasicOrder(List<OrderSpecifier<?>> order, QAuctionItem item) {
    if (this.basic) {
      order.add(item.createdAt.desc());
    }
  }
}
