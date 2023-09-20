package megabrain.gyeongnamgyeongmae.domain.user.exception;

public class DuplicateAuthVendorUserId extends RuntimeException {
  public DuplicateAuthVendorUserId() {}

  public DuplicateAuthVendorUserId(String message) {
    super(message);
  }

  public DuplicateAuthVendorUserId(String message, Throwable cause) {
    super(message, cause);
  }
}
