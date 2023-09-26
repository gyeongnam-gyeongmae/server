package megabrain.gyeongnamgyeongmae.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

  private final UserService userService;

  @Operation(summary = "유저 전체조회", description = "유저 모두를 조회합니다")
  @GetMapping("")
  public ResponseEntity<List<User>> findAllUser() {
    List<User> users = userService.findAllUser();
    return ResponseEntity.ok(users);
  }
}
