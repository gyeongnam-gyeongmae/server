package megabrain.gyeongnamgyeongmae.domain.authentication.exception;

public class OAuthLoginException extends RuntimeException {
  public OAuthLoginException() {}

  public OAuthLoginException(String message) {
    super(message);
  }

  public OAuthLoginException(String message, Throwable cause) {
    super(message, cause);
  }
}
