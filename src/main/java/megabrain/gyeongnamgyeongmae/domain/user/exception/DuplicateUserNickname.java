package megabrain.gyeongnamgyeongmae.domain.user.exception;

public class DuplicateUserNickname extends RuntimeException {
  public DuplicateUserNickname() {}

  public DuplicateUserNickname(String message) {
    super(message);
  }

  public DuplicateUserNickname(String message, Throwable cause) {
    super(message, cause);
  }
}
