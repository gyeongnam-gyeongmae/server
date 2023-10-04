package megabrain.gyeongnamgyeongmae.global.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.web.client.HttpHeadersProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class AdminSecurityConfig {

  private final AdminServerProperties adminServer;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    final SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler =
        new SavedRequestAwareAuthenticationSuccessHandler();
    loginSuccessHandler.setTargetUrlParameter("redirectTo");
    loginSuccessHandler.setDefaultTargetUrl(this.adminServer.path("/"));

    http.authorizeHttpRequests()
        .requestMatchers(
            new AntPathRequestMatcher("/login"), new AntPathRequestMatcher("/assets/**"))
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginPage(this.adminServer.path("/login"))
        .successHandler(loginSuccessHandler)
        .and()
        .logout()
        .logoutUrl("/logout")
        .and()
        .httpBasic()
        .and()
        .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .ignoringRequestMatchers(
            new AntPathRequestMatcher(this.adminServer.path("/instances")),
            new AntPathRequestMatcher(this.adminServer.path("/actuator/**")));

    return http.build();
  }

  @Bean
  public HttpHeadersProvider customHttpHeadersProvider() {
    return (instance) -> {
      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.add("X-CUSTOM", "My Custom Value");
      return httpHeaders;
    };
  }
}
