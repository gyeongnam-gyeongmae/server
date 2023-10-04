package megabrain.gyeongnamgyeongmae.domain.auctionItem.exception;

public class AuctionOwnerCanNotBid extends RuntimeException {
  public AuctionOwnerCanNotBid() {}

  public AuctionOwnerCanNotBid(String message) {
    super(message);
  }

  public AuctionOwnerCanNotBid(String message, Throwable cause) {
    super(message, cause);
  }
}
