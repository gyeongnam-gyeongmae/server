package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuctionItemCommentRequest {

    private @NotEmpty String content;
    private @NotEmpty Long userId;
    private @NotEmpty Long parentCommentId;

}
