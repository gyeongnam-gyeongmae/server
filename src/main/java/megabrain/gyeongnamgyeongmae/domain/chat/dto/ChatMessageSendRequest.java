package megabrain.gyeongnamgyeongmae.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.MessageTypeEnum;

@Getter
@Setter
@AllArgsConstructor
public class ChatMessageSendRequest {
  private Long userId;
  private String content;
  private MessageTypeEnum messageType;
}
