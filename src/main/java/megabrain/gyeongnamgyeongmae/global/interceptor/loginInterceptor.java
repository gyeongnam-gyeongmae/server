package megabrain.gyeongnamgyeongmae.global.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.authentication.exception.UnAuthenticatedException;
import megabrain.gyeongnamgyeongmae.domain.authentication.service.AuthenticationService;
import megabrain.gyeongnamgyeongmae.global.anotation.LoginRequired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class loginInterceptor implements HandlerInterceptor {
  private final AuthenticationService authenticationService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    if (handler instanceof HandlerMethod
        && ((HandlerMethod) handler).hasMethodAnnotation(LoginRequired.class)) {
      Long memberId = authenticationService.getLoginUserId();

      if (memberId == null) {
        throw new UnAuthenticatedException();
      }
    }

    return true;
  }
}
