package megabrain.gyeongnamgyeongmae.domain.user.controller;

import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
}
