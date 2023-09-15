package megabrain.gyeongnamgyeongmae.domain.auctionItem.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItemStatus;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionStatus;
import megabrain.gyeongnamgyeongmae.domain.member.domain.entity.Address;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuctionItemResponse {

  private Long id;

  private String name;

  private String nickname;
  private Long price;
  private AuctionItemStatus itemStatus;
  private AuctionStatus auctionStatus;

  private LocalDateTime createdTime;
  private LocalDateTime closeTime;
  private LocalDateTime modifiedTime;

  private Address address;
  private String category;

  private String content;

  private Long likeCount;
  private Long viewCount;

  public static AuctionItemResponse of(AuctionItem auctionItem) {
    return AuctionItemResponse.builder()
        .id(auctionItem.getId())
        .nickname(auctionItem.getMember().getNickname())
        .name(auctionItem.getName())
        .price(auctionItem.getPrice())
        .itemStatus(auctionItem.getItemStatus())
        .auctionStatus(auctionItem.getStatus())
        .createdTime(auctionItem.getCreatedAt())
        .modifiedTime(auctionItem.getUpdatedAt())
        .closeTime(auctionItem.getClosedTime())
        .address(auctionItem.getMember().getAddress())
        .category(auctionItem.getCategory().getName())
        .content(auctionItem.getContent())
        .likeCount(auctionItem.getLike_count())
        .viewCount(auctionItem.getView_count())
        .build();
  }
}
