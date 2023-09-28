package megabrain.gyeongnamgyeongmae.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  // 메시지 브로커가 해당 prefix를 붙인 메시지를 읽어서 클라이언트에게 전달합니다.
  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/subscribe"); // client에서 subscribe할때 붙여줄 prefix
    config.setApplicationDestinationPrefixes("/publish"); // client에서 send할때 붙여줄 prefix
  }

  // STOMP(Simple Text Oriented Message Protocol): 웹소켓을 통해 메시지를 전달하기 위한 프로토콜
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    // websocket을 사용할수 있는 endpoint를 등록합니다.
    // sock.js를 통하여 낮은 버전의 브라우저에서도 websocket이 동작할수 있게 합니다.
    registry.addEndpoint("/connect").setAllowedOrigins("*");
    registry.addEndpoint("/connect").setAllowedOrigins("*").withSockJS();
  }
}
