package megabrain.gyeongnamgyeongmae.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class RedisMessageConfig {

  @Value("${spring.redis.host}")
  private String host;

  private int port = 6379;
}
