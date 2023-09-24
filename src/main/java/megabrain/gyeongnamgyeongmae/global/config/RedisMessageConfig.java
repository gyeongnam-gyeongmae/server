package megabrain.gyeongnamgyeongmae.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisMessageConfig {

  private String host = "gg-redis";

  private int port = 6379;
}
