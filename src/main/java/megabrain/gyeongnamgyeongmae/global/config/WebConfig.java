package megabrain.gyeongnamgyeongmae.global.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.global.interceptor.loginInterceptor;
import megabrain.gyeongnamgyeongmae.global.resolver.LoginUserArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
  private final loginInterceptor loginInterceptor;
  private final LoginUserArgumentResolver loginUserArgumentResolver;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loginInterceptor);
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(loginUserArgumentResolver);
  }
}
