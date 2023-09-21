package megabrain.gyeongnamgyeongmae.domain.authentication.exception;

public class UnAuthenticatedException extends RuntimeException {

  public UnAuthenticatedException() {}

  public UnAuthenticatedException(String message) {
    super(message);
  }

  public UnAuthenticatedException(String message, Throwable cause) {
    super(message, cause);
  }
}
