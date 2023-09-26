package megabrain.gyeongnamgyeongmae.domain.chat.domain.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatParticipantId implements Serializable {
  private Long chatRoom;
  private Long user;
}
