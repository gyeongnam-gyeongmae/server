package megabrain.gyeongnamgyeongmae.domain.chat.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatMessage;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecentChatMessageResponse implements Serializable {
  private Long roomId;
  private Long chatId;
  private Long userId;
  private String content;
  private LocalDateTime createdAt;

  public static RecentChatMessageResponse of(ChatMessage chatMessage) {
    return RecentChatMessageResponse.builder()
        .roomId(chatMessage.getRoom().getId())
        .chatId(chatMessage.getId())
        .userId(chatMessage.getUser().getId())
        .content(chatMessage.getContent())
        .createdAt(chatMessage.getCreatedAt())
        .build();
  }
}
