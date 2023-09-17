package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuctionItemPaginationDto {

    private Long currentPage;

    private Long itemCount;

    @NotEmpty
    private Long itemsPerPage = 10L;

    private Long totalItems;

    private Long totalPages;
}
