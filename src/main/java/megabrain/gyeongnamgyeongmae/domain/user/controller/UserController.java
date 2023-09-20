package megabrain.gyeongnamgyeongmae.domain.user.controller;

import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserService;
<<<<<<< HEAD
=======
import org.springframework.security.crypto.password.PasswordEncoder;
>>>>>>> 4a39bbb72f5763f76cf87b49cd016a02904318bf
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

  private final UserService userService;
<<<<<<< HEAD
=======
  private final PasswordEncoder passwordEncoder;
>>>>>>> 4a39bbb72f5763f76cf87b49cd016a02904318bf
}
