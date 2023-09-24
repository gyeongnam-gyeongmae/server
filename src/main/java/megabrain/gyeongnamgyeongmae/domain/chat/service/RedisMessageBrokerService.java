package megabrain.gyeongnamgyeongmae.domain.chat.service;

import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.chat.dto.RecentChatMessageResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisMessageBrokerService {
  private final RedisTemplate<String, RecentChatMessageResponse> redisTemplate;
  private final ChannelTopic channelTopic;

  public void sender(RecentChatMessageResponse response) {
    redisTemplate.convertAndSend(getTopic(), response);
  }

  public String getTopic() {
    return channelTopic.getTopic();
  }
}
