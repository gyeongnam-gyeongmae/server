package megabrain.gyeongnamgyeongmae.domain.user.dto.UserProfile;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AuctionItemLikedResponse {
    private List<Long> auctionItemId;
}
