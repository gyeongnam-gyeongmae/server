package megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity;

public enum AuctionItemStatus {
  NEW("신품"),
  USED("중고품");

  private final String status;

  AuctionItemStatus(String status) {
    this.status = status;
  }

  public String getAuctionItemStatus() {
    return this.status;
  }
}
