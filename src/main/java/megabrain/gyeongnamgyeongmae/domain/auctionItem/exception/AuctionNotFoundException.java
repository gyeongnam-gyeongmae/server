package megabrain.gyeongnamgyeongmae.domain.auctionItem.exception;

public class AuctionNotFoundException extends RuntimeException {
  public AuctionNotFoundException() {}

  public AuctionNotFoundException(String message) {
    super(message);
  }

  public AuctionNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
