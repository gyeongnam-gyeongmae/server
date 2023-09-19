package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuctionItemLikeRequest {

    @NotNull
    private Long memberId;

}
