package megabrain.gyeongnamgyeongmae.global.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.chat.dto.RecentChatMessageResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisMessageSetting {

  private static final String TOPIC_NAME = "CHAT_ROOM";
  private final RedisMessageConfig redisMessageConfig;

  @Bean
  public RedisConnectionFactory redisMessageConnectionFactory() {
    RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
    configuration.setHostName(redisMessageConfig.getHost());
    configuration.setPort(redisMessageConfig.getPort());
    return new LettuceConnectionFactory(configuration);
  }

  //  @Bean
  //  public RedisMessageListenerContainer redisMessageListenerContainer(
  //      RedisConnectionFactory redisConnectionFactory, RedisSubscriber redisSubscriber) {
  //    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
  //    container.setConnectionFactory(redisConnectionFactory);
  //    container.addMessageListener(redisSubscriber, channelTopic());
  //    return container;
  //  }

  @Bean
  public RedisTemplate<String, RecentChatMessageResponse> redisMessageTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, RecentChatMessageResponse> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper()));
    return redisTemplate;
  }

  @Bean
  public ChannelTopic channelTopic() {
    return new ChannelTopic(TOPIC_NAME);
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper()
        .findAndRegisterModules()
        .enable(SerializationFeature.INDENT_OUTPUT)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerModules(new JavaTimeModule(), new Jdk8Module());
  }
}
