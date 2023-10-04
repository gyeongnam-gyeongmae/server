package megabrain.gyeongnamgyeongmae.global.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.SseEmitterService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisSseSubscriber implements MessageListener {

  private final Map<String, String> channelLists = new ConcurrentHashMap();

  private final RedisMessageListenerContainer redisContainer;
  private final RedisTemplate redisTemplate;
  private final SseEmitterService sseEmitterService;

  public void createChannel(String channelName) {
    if (!channelLists.containsKey(channelName)) {
      redisContainer.addMessageListener(this, new ChannelTopic(channelName));
      channelLists.putIfAbsent(channelName, channelName);
    }
  }

  public void removeChannel(String channelName) {
    if (channelLists.containsKey(channelName)) {
      redisContainer.removeMessageListener(this, new ChannelTopic(channelName));
      // value = null 이면 해당 키 값 제거
      channelLists.computeIfPresent(channelName, (key, value) -> null);
    }
  }

  @Override
  public void onMessage(Message message, byte[] pattern) {
    String channelName =
        (String) redisTemplate.getStringSerializer().deserialize(message.getChannel());
    String productPrice =
        (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
    int connectedSseEmittersSize = sseEmitterService.notify(channelName, productPrice);
    if (connectedSseEmittersSize == 0) {
      removeChannel(channelName);
    }
  }
}
