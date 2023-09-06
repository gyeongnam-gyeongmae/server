package megabrain.gyeongnamgyeongmae.auctionItem.dto.SearchItem;

import lombok.*;

@Data
@Getter
@Setter
public class SearchAuctionItemSortedRequest {

  private String category;

  private SearchStatus search_status = SearchStatus.ALL; // 신품 중고품 전체

  private Boolean closed = Boolean.FALSE; // 이미 경매가 끝낸 개시글도 보기

  private Boolean search_time = Boolean.FALSE; // 마감시간 근접

  private Boolean like = Boolean.FALSE; // 좋아요 정렬 기준

  private Boolean search_price = Boolean.FALSE; // 가격순 정렬

  private Boolean view_count = Boolean.FALSE; // 조회수 정렬 기준

  //  @NotNull private Location my_location; // 나의 현재 위치

  //  @NotNull private int km; // 몇키로 반경 게시글 조회

  //  @NotNull private int town; // 몇개의 마을 조회

  //  @NotNull private boolean comment; // 댓글수 정렬 기준

  //  @NotNull private String member; // 이름 검색

  //  @NotNull private boolean temperature; // 온도별 정렬 기준

}
