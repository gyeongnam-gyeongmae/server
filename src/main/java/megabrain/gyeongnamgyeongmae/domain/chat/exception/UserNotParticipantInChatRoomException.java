package megabrain.gyeongnamgyeongmae.domain.chat.exception;

public class UserNotParticipantInChatRoomException extends RuntimeException {
  public UserNotParticipantInChatRoomException() {}

  public UserNotParticipantInChatRoomException(String message) {
    super(message);
  }

  public UserNotParticipantInChatRoomException(String message, Throwable cause) {
    super(message, cause);
  }
}
