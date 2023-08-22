package megabrain.gyeongnamgyeongmae.auctionItem.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItemStatus;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionStatus;
import megabrain.gyeongnamgyeongmae.member.domain.entity.Address;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuctionItemResponse {

  private Long id;

  private String name;

  private String nickname;
  private int price;
  private AuctionItemStatus itemStatus;
  private AuctionStatus auctionStatus;

  private LocalDateTime createdTime;
  private LocalDateTime closeTime;
  private LocalDateTime modifiedTime;

  private Address address;
  private String category;
  private String email;

  private String content;

  private int likeCount;
  private int viewCount;

  public static AuctionItemResponse of(AuctionItem auctionItem) {
    return AuctionItemResponse.builder()
        .id(auctionItem.getId())
        .nickname(auctionItem.getMember().getNickname())
        .name(auctionItem.getName())
        .price(auctionItem.getPrice())
        .itemStatus(auctionItem.getContent().getStatus())
        .auctionStatus(auctionItem.getStatus())
        .createdTime(auctionItem.getCreatedAt())
        .modifiedTime(auctionItem.getUpdatedAt())
        .closeTime(auctionItem.getClosedTime())
        .address(auctionItem.getMember().getAddress())
        .category(auctionItem.getCategory().getName())
        .email(auctionItem.getMember().getEmail())
        .content(auctionItem.getContent().getContent())
        .likeCount(auctionItem.getLike_count())
        .viewCount(auctionItem.getView_count())
        .build();
  }
}