package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Data
public class AuctionItemCommentDeleteRequest {

    private @NotEmpty Long userId;
    private @NotEmpty Long commentId;
}
