package megabrain.gyeongnamgyeongmae.fixture;

import java.time.LocalDateTime;
import megabrain.gyeongnamgyeongmae.Category.domain.entity.Category;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItemStatus;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.Content;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.CreateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.member.domain.entity.Member;

public class AuctionItemFixture {
  public static final Category NEW_CATEGORY = new Category("phone");

  public static final Member member = new Member("test1@gmail.com", "!Test123", "test1");

  public static CreateAuctionItemRequest CREATE_AUCTION_ITEM =
      new CreateAuctionItemRequest(
          "휴대폰 경매합니다",
          10000,
          "phone",
          new Content("content", AuctionItemStatus.NEW),
          LocalDateTime.parse("2098-12-31T12:00"),
          1L);

  public static AuctionItem AUCTION_ITEM =
      new AuctionItem(
          1L,
          "휴대폰 경매합니다",
          10000,
          new Content("content", AuctionItemStatus.NEW),
          LocalDateTime.parse("2098-12-31T12:00:00"),
          NEW_CATEGORY,
          member,
          LocalDateTime.parse("2023-12-31T12:00:00"),
          LocalDateTime.parse("2023-12-31T12:00:00"));
}
