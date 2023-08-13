package megabrain.gyeongnamgyeongmae.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/members")
public class MemberController {

  @PostMapping("register")
  public ResponseEntity<HttpStatus> registerMember() {

    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
