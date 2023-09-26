package megabrain.gyeongnamgyeongmae.domain.user.exception;

public class InvalidateCoordinate extends RuntimeException {
  public InvalidateCoordinate() {}

  public InvalidateCoordinate(String message) {
    super(message);
  }

  public InvalidateCoordinate(String message, Throwable cause) {
    super(message, cause);
  }
}
