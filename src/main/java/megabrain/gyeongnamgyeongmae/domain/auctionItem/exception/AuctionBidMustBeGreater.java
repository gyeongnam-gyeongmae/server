package megabrain.gyeongnamgyeongmae.domain.auctionItem.exception;

public class AuctionBidMustBeGreater extends RuntimeException {
  public AuctionBidMustBeGreater() {}

  public AuctionBidMustBeGreater(String message) {
    super(message);
  }

  public AuctionBidMustBeGreater(String message, Throwable cause) {
    super(message, cause);
  }
}
