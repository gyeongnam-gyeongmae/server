package megabrain.gyeongnamgyeongmae.domain.authentication.controller;

import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.authentication.dto.PhoneAuthenticationRequest;
import megabrain.gyeongnamgyeongmae.global.infra.email.AuthenticationServiceInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/authentications")
public class AuthenticationController {
  private final AuthenticationServiceInterface authenticationService;

  @PostMapping("register")
  @Operation(summary = "회원가입 요청", description = "회원의 회원가입을 요청합니다.")
  public ResponseEntity<HttpStatus> memberRegister() {
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("phone")
  @Operation(summary = "휴대전화 인증번호 검증", description = "회원가입 전 올바른 휴대전화 인증번호인지 여부를 요청합니다.")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<HttpStatus> checkPhoneAuthenticationCode(
      @RequestParam String phoneNumber, @RequestParam String code) {

    boolean exist = authenticationService.isPhoneAuthenticationCodeExist(phoneNumber, code);

    if (!exist) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("phone")
  @Operation(summary = "휴대전화 인증 요청", description = "휴대전화 번호를 통해 인증번호 송신을 요청합니다.")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<HttpStatus> authenticationByMail(
      @RequestBody @Valid PhoneAuthenticationRequest phoneAuthenticationRequest) {
    String phoneAuthenticationCode = authenticationService.generatePhoneAuthenticationCode();

    authenticationService.sendPhoneAuthenticationCode(
        phoneAuthenticationRequest.getPhoneNumber(), phoneAuthenticationCode);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
