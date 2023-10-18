package megabrain.gyeongnamgyeongmae.domain.chat.domain.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatParticipantId implements Serializable {
  private Long chatRoom;
  private Long user;

  public ChatParticipantId() {}

  private ChatParticipantId(Long chatRoomId, Long userId) {
    this.chatRoom = chatRoomId;
    this.user = userId;
  }

  public static ChatParticipantId from(Long chatRoomId, Long userId) {
    return new ChatParticipantId(chatRoomId, userId);
  }
}
