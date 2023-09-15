package megabrain.gyeongnamgyeongmae.domain.authentication.exception;

public class OAuthVendorNotFoundException extends RuntimeException {
  public OAuthVendorNotFoundException() {}

  public OAuthVendorNotFoundException(String message) {
    super(message);
  }

  public OAuthVendorNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
