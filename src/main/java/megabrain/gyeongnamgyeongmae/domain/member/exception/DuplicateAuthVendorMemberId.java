package megabrain.gyeongnamgyeongmae.domain.member.exception;

public class DuplicateAuthVendorMemberId extends RuntimeException {
  public DuplicateAuthVendorMemberId() {}

  public DuplicateAuthVendorMemberId(String message) {
    super(message);
  }

  public DuplicateAuthVendorMemberId(String message, Throwable cause) {
    super(message, cause);
  }
}
