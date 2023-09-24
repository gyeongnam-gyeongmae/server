package megabrain.gyeongnamgyeongmae.global.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.chat.dto.RecentChatMessageResponse;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {
  private final ObjectMapper objectMapper;
  private final SimpMessageSendingOperations messagingTemplate;

  @Override
  public void onMessage(Message message, byte[] pattern) {
    try {
      String subscribeMessage = new String(message.getBody());
      RecentChatMessageResponse response =
          objectMapper.readValue(subscribeMessage, RecentChatMessageResponse.class);
      messagingTemplate.convertAndSend(getSendTopic(response.getRoomId()), response);
      // log.info("[INFO] MessageListener TOPIC ID : {} ", getSendTopic(response.getRoomId()));
    } catch (JsonProcessingException e) {
      // log.error("[Error] MessageListener ", e);
    }
  }

  private String getSendTopic(Long id) {
    return String.format("/topic/room/%d", id);
  }
}
