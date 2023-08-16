package megabrain.gyeongnamgyeongmae.auctionItem.domain.entity;

public enum AuctionStatus {
  ONGOING("진행중"),
  CLOSED("경매마감"),
  BIDDING("입찰중"),
  SOLD("낙찰완료");

  private final String status;

  AuctionStatus(String status) {
    this.status = status;
  }

  public String getAuctionStatus() {
    return this.status;
  }
}
