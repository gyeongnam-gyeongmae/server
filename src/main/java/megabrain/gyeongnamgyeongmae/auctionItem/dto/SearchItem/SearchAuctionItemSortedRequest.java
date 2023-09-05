package megabrain.gyeongnamgyeongmae.auctionItem.dto.SearchItem;

import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SearchAuctionItemSortedRequest {

  //  @NotNull private SearchStatus search_status; // 신품 중고품 전체

  //  @NotNull private boolean closed; // 이미 경매가 끝낸 개시글도 보기

  //  @NotNull private SearchTime search_time; // 마감시간 근접, 멀리

  //  @NotNull private Location my_location; // 나의 현재 위치

  //  @NotNull private int km; // 몇키로 반경 게시글 조회
  //
  //  @NotNull private int town; // 몇개의 마을 조회

  @NotNull private String category; // 카테고리

  //  private String member; // 이름 검색
  //
  //  private boolean like; // 좋아요 정렬 기준
  //
  //  private boolean comment;  // 댓글수 정렬 기준
  //
  //  private boolean temperature; // 온도별 정렬 기준

  //  @NotNull private SearchPrice search_price; // 가격순 정렬
}
