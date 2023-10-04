package megabrain.gyeongnamgyeongmae;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class GyeongnamGyeongmaeApplication {

  public static void main(String[] args) {
    SpringApplication.run(GyeongnamGyeongmaeApplication.class, args);
  }
}
