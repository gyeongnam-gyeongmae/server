package megabrain.gyeongnamgyeongmae.domain.authentication.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.authentication.dto.MailAuthenticationRequest;
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

  @PostMapping("mail")
  public ResponseEntity<HttpStatus> authenticationByMail(
      @RequestBody @Valid MailAuthenticationRequest mailAuthenticationRequest) {
    String mailAuthenticationCode = authenticationService.generateMailAuthenticationCode();
    String mailSubject = "경남경매 인증 메일";
    String mailContent = "경남경매 인증을 위해 다음 링크를 클릭해주세요.<br/>" + mailAuthenticationCode;

    authenticationService.sendAuthenticationMail(
        mailAuthenticationRequest.getEmail(), mailSubject, mailContent);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("phone")
  public ResponseEntity<HttpStatus> authenticationByMail(
      @RequestBody @Valid PhoneAuthenticationRequest phoneAuthenticationRequest) {
    String phoneAuthenticationCode =
        authenticationService.generateCellularPhoneAuthenticationCode();

    authenticationService.sendCellularPhoneAuthenticationCode(
        phoneAuthenticationRequest.getPhoneNumber(), phoneAuthenticationCode);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
