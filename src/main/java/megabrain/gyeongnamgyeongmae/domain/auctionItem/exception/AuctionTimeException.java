package megabrain.gyeongnamgyeongmae.domain.auctionItem.exception;

public class AuctionTimeException extends RuntimeException {
    public AuctionTimeException () {}

    public AuctionTimeException (String message) {
        super(message);
    }

    public AuctionTimeException (String message, Throwable cause) {
        super(message, cause);
    }
}
