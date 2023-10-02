package megabrain.gyeongnamgyeongmae.domain.chat.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatMessage;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.MessageTypeEnum;

@Getter
@Setter
@AllArgsConstructor
public class ChatMessageResponse {
  private Long userId;
  private Long chatId;
  private String content;
  private MessageTypeEnum messageType;
  private LocalDateTime createdAt;

  private ChatMessageResponse(ChatMessage chatMessage) {
    this.chatId = chatMessage.getId();
    this.userId = chatMessage.getUser().getId();
    this.content = chatMessage.getContent();
    this.messageType = chatMessage.getType();
    this.createdAt = chatMessage.getCreatedAt();
  }

  public static ChatMessageResponse of(ChatMessage chatMessage) {
    return new ChatMessageResponse(chatMessage);
  }
}
