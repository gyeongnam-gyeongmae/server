package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem;

import lombok.Data;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.AuctionItemFirstView;

import java.util.List;

@Data
public class AuctionItemSearchResponse {

    private List<AuctionItemFirstView> auctionItemFirstViewPage;
    private AuctionItemPaginationDto auctionItemPaginationDto;
}
