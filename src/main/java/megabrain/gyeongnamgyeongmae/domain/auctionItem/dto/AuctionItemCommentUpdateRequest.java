package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto;


import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class AuctionItemCommentUpdateRequest {
    private @NotEmpty String content;
    private @NotEmpty Long userId;
    private @NotEmpty Long commentId;

}
