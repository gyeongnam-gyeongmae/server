package megabrain.gyeongnamgyeongmae.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisSsePublisher {
  private final RedisTemplate redisTemplate;

  public void publish(String channelName, Long price) {
    redisTemplate.convertAndSend(channelName, price);
  }
}
