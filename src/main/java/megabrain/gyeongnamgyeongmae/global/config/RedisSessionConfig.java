package megabrain.gyeongnamgyeongmae.global.config;

// @Configuration
// @EnableRedisHttpSession
// public class RedisSessionConfig {
//  @Value("${spring.redis.host}")
//  private String host;
//
//  @Value("${spring.redis.port}")
//  private int port;
//
//  private final String redisSessionBeanName = "redisSessionConnectionFactory";
//
//  @Primary
//  @Bean(name = redisSessionBeanName)
//  public RedisConnectionFactory redisConnectionFactory() {
//    return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port));
//  }
//
//  @Bean
//  public RedisTemplate<String, Object> redisTemplate(
//      @Qualifier(redisSessionBeanName) RedisConnectionFactory redisConnectionFactory) {
//    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//    redisTemplate.setConnectionFactory(redisConnectionFactory);
//    redisTemplate.setKeySerializer(new StringRedisSerializer());
//    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//
//    return redisTemplate;
//  }
// }
