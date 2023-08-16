package megabrain.gyeongnamgyeongmae;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GyeongnamGyeongmaeApplication {

  public static void main(String[] args) {
    SpringApplication.run(GyeongnamGyeongmaeApplication.class, args);
  }
}
