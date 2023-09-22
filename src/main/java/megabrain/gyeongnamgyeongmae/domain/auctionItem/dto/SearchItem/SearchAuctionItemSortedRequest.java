package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;

import java.util.List;

import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemStatus;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionStatus;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.QAuctionItem;

import javax.validation.constraints.NotEmpty;


@Data
public class SearchAuctionItemSortedRequest {

    @NotEmpty
    private String keyword = null;

    @NotEmpty
    private String category = null;

//    @NotEmpty
//    private SearchStatus search_status;

    @NotEmpty
    private Boolean closed;

    @NotEmpty
    private Boolean search_time;

//    @NotEmpty
//    private Boolean like;

    @NotEmpty
    private Boolean search_price;

//    @NotEmpty
//    private Boolean view_count;

    @NotEmpty
    private Long page;

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

//    public void applySearchStatus(BooleanBuilder status, QAuctionItem item) {
//        if (this.search_status == SearchStatus.NEW) {
//            status.and(item.itemStatus.eq(AuctionItemStatus.NEW));
//        } else if (this.search_status == SearchStatus.USED) {
//            status.and(item.itemStatus.eq(AuctionItemStatus.USED));
//        } else {
//            status.and(item.itemStatus.in(AuctionItemStatus.NEW, AuctionItemStatus.USED));
//        }
//    }

    public void applySearchPrice(List<OrderSpecifier<?>> order, QAuctionItem item) {
        if (this.search_price) {
            order.add(item.price.desc());
        } else {
            order.add(item.price.asc());
        }
    }

//    public void applySearchLike(List<OrderSpecifier<?>> order, QAuctionItem item) {
//        if (this.like) {
//            order.add(item.like_count.desc());
//        } else {
//            order.add(item.like_count.asc());
//        }
//    }

//    public void applySearchView(List<OrderSpecifier<?>> order, QAuctionItem item) {
//        if (this.view_count) {
//            order.add(item.view_count.desc());
//        } else {
//            order.add(item.view_count.asc());
//        }
//    }

    public void applySearchTime(List<OrderSpecifier<?>> order, QAuctionItem item) {
        if (this.search_time) {
            order.add(item.createdAt.desc());
        } else {
            order.add(item.createdAt.asc());
        }
    }

    public void applySearchClosed(BooleanBuilder builder, QAuctionItem item) {
        if (this.closed) {
            builder.and(item.status.eq(AuctionStatus.ONGOING));
        } else {
            builder.and(item.status.in(AuctionStatus.ONGOING, AuctionStatus.CLOSED, AuctionStatus.BIDDING));
        }
    }

    public SearchAuctionItemSortedRequest() {
//        this.search_status = SearchStatus.ALL;
        this.closed = Boolean.FALSE;
        this.search_time = Boolean.FALSE;
//        this.like = Boolean.FALSE;
        this.search_price = Boolean.FALSE;
//        this.view_count = Boolean.FALSE;
    }

}

