package megabrain.gyeongnamgyeongmae.domain.member.controller;

import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.member.service.MemberService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/members")
public class MemberController {

  private final MemberService memberService;
  private final PasswordEncoder passwordEncoder;
}
