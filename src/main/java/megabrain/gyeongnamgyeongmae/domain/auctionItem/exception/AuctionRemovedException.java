package megabrain.gyeongnamgyeongmae.domain.auctionItem.exception;

public class AuctionRemovedException extends RuntimeException {
  public AuctionRemovedException() {}

  public AuctionRemovedException(String message) {
    super(message);
  }

  public AuctionRemovedException(String message, Throwable cause) {
    super(message, cause);
  }
}
