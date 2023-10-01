package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionStatus;
import megabrain.gyeongnamgyeongmae.domain.image.domain.entity.Image;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.Address;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuctionItemFirstView {

  private Long id;
  //    private String nickname;

  private String name;

  private LocalDateTime closedTime;

  //    private AuctionItemStatus auctionItemStatus;

  private AuctionStatus status;

  private Address address;

  private Long like_count;

  private Long view_count;

  private Long price;
  // 나중에 실시간 가격 추가시 수정
  private Long now_price;

  private String image_url;

  private Long comment_count;

  @Builder
  public static AuctionItemFirstView of(AuctionItem auctionItem, Image image) {
    return AuctionItemFirstView.builder()
        .id(auctionItem.getId())
        //                .nickname(auctionItem.getUser().getNickname())
        .name(auctionItem.getName())
        .address(auctionItem.getAddress())
        .like_count(auctionItem.getLike_count())
        .view_count(auctionItem.getView_count())
        .price(auctionItem.getPrice())
        .closedTime(auctionItem.getClosedTime())
        .comment_count(auctionItem.getComment_count())
        //                .auctionItemStatus(auctionItem.getItemStatus())
        .status(auctionItem.getStatus())
        .now_price(20000L)
        .image_url(Optional.ofNullable(image).map(Image::getImageUrl).orElse(null))
        .build();
  }
}
