package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemStatus;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionStatus;
import megabrain.gyeongnamgyeongmae.domain.member.domain.entity.Address;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuctionItemFirstView {

    private Long id;

    private String name;

    private LocalDateTime closedTime;

    private AuctionItemStatus auctionItemStatus;

    private AuctionStatus status;

    private Address address;

    private Long like_count;

    private Long view_count;

    private Long price;

    @Builder
    public static AuctionItemFirstView of(AuctionItem auctionItem) {
        return AuctionItemFirstView.builder()
                .id(auctionItem.getId())
                .name(auctionItem.getName())
                .address(auctionItem.getMember().getAddress())
                .like_count(auctionItem.getLike_count())
                .view_count(auctionItem.getView_count())
                .price(auctionItem.getPrice())
                .closedTime(auctionItem.getClosedTime())
                .auctionItemStatus(auctionItem.getItemStatus())
                .status(auctionItem.getStatus())
                .build();
    }

}