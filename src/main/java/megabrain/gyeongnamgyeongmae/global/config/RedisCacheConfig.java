package megabrain.gyeongnamgyeongmae.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class RedisCacheConfig {

  @Value("${spring.redis.cache.host}")
  private String host;

  @Value("${spring.redis.cache.port}")
  private int port;
}
