package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemStatus;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateAuctionItemRequest {

    @NotNull
    private String name;

    @NotNull
    private Long price;

    @NotNull
    private String category;

    @NotNull
    private String content;

    @NotNull
    private AuctionItemStatus status;

    @NotNull
    private LocalDateTime closedTime;

    @NotNull
    private Long member;

    public AuctionItem toEntity() {
        return AuctionItem.builder()
                .name(this.name)
                .price(this.price)
                .content(this.content)
                .itemStatus(this.status)
                .closedTime(this.closedTime)
                .build();
    }
}
