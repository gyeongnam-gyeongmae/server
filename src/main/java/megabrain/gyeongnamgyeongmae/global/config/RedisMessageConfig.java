package megabrain.gyeongnamgyeongmae.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class RedisMessageConfig {

  private String host = "localhost";

  private int port = 6379;
}
