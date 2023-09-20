package megabrain.gyeongnamgyeongmae.global.resolver;

import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.authentication.service.AuthenticationService;
import megabrain.gyeongnamgyeongmae.global.anotation.LoginUser;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

  private final AuthenticationService authenticationService;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(LoginUser.class);
  }

  @Override
  public Object resolveArgument(
      MethodParameter parameter,
      ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest,
      WebDataBinderFactory binderFactory)
      throws Exception {
    return authenticationService.getLoginUser();
  }
}
