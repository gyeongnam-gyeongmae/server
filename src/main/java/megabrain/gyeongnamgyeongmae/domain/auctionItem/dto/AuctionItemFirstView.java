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

  private String name;

  private String nickname;

  private LocalDateTime closedTime;

  private AuctionStatus status;

  private Address address;

  private Long like_count;

  private Long view_count;

  private Long price;
  // 나중에 실시간 가격 추가시 수정
  private Long now_price;

  private String image_url;

  private Long comment_count;

  private Boolean isLike;

  @Builder
  public static AuctionItemFirstView of(AuctionItem auctionItem, Image image, Boolean isLike) {
    return AuctionItemFirstView.builder()
        .id(auctionItem.getId())
        .nickname(auctionItem.getUser().getNickname())
        .name(auctionItem.getName())
        .address(auctionItem.getUser().getAddress())
        .like_count(auctionItem.getLike_count())
        .view_count(auctionItem.getView_count())
        .price(auctionItem.getPrice())
        .closedTime(auctionItem.getClosedTime())
        .comment_count(auctionItem.getComment_count())
        .status(auctionItem.getStatus())
        .now_price(auctionItem.getNowPrice())
        .image_url(Optional.ofNullable(image).map(Image::getImageUrl).orElse(null))
        .isLike(Optional.ofNullable(isLike).orElse(false))
        .build();
  }
}
