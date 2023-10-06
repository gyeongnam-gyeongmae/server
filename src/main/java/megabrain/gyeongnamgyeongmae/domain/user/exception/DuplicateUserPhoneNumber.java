package megabrain.gyeongnamgyeongmae.domain.user.exception;

public class DuplicateUserPhoneNumber extends RuntimeException {
  public DuplicateUserPhoneNumber() {}

  public DuplicateUserPhoneNumber(String message) {
    super(message);
  }

  public DuplicateUserPhoneNumber(String message, Throwable cause) {
    super(message, cause);
  }
}
