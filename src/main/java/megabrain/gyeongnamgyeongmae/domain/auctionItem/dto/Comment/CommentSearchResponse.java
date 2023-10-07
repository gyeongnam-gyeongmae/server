package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.Comment;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.dto.SearchItem.AuctionItemPaginationDto;

@Data
public class CommentSearchResponse {

  private List<CommentFirstView> commentFirstViewPage;
  private AuctionItemPaginationDto auctionItemPaginationDto;

  @Builder
  public CommentSearchResponse(
      List<CommentFirstView> commentFirstViewPage,
      AuctionItemPaginationDto auctionItemPaginationDto) {
    this.commentFirstViewPage = commentFirstViewPage;
    this.auctionItemPaginationDto = auctionItemPaginationDto;
  }
}
