package megabrain.gyeongnamgyeongmae.domain.member.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.member.domain.entity.Member;
import megabrain.gyeongnamgyeongmae.domain.member.dto.MemberCreateRequest;
import megabrain.gyeongnamgyeongmae.domain.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/members")
public class MemberController {

  private final MemberService memberService;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("/register")
  public ResponseEntity<HttpStatus> registerMember(
      @RequestBody @Valid MemberCreateRequest memberCreateRequest) {

    boolean isDuplicated = this.memberService.isDuplicatedEmail(memberCreateRequest.getEmail());
    if (isDuplicated) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    Member member = MemberCreateRequest.toEntity(memberCreateRequest, passwordEncoder);
    memberService.createMember(member);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}