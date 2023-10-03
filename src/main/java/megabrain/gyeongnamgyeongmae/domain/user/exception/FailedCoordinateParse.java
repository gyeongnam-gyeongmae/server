package megabrain.gyeongnamgyeongmae.domain.user.exception;

public class FailedCoordinateParse extends RuntimeException {
  public FailedCoordinateParse() {}

  public FailedCoordinateParse(String message) {
    super(message);
  }

  public FailedCoordinateParse(String message, Throwable cause) {
    super(message, cause);
  }
}
