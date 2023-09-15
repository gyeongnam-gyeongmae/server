package megabrain.gyeongnamgyeongmae.domain.authentication.controller;

import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthUserProfile;
import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthVendorName;
import megabrain.gyeongnamgyeongmae.domain.authentication.dto.MemberRegisterRequest;
import megabrain.gyeongnamgyeongmae.domain.authentication.dto.PhoneAuthenticationRequest;
import megabrain.gyeongnamgyeongmae.domain.authentication.service.AuthenticationServiceInterface;
import megabrain.gyeongnamgyeongmae.domain.member.domain.entity.Member;
import megabrain.gyeongnamgyeongmae.domain.member.domain.repository.MemberRepository;
import megabrain.gyeongnamgyeongmae.domain.member.exception.DuplicateAuthVendorMemberId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/authentications")
public class AuthenticationController {
  private final AuthenticationServiceInterface authenticationService;
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("register/{auth-vendor}")
  @Operation(summary = "회원가입 요청", description = "회원의 회원가입을 요청합니다.")
  public ResponseEntity<HttpStatus> memberRegisterWithOAuthVendor(
      @PathVariable("auth-vendor") String authVendorName,
      @RequestBody() @Valid MemberRegisterRequest memberRegisterRequest) {
    authenticationService.isValidateOAuthVendorName(authVendorName);
    OAuthVendorName vendorName = OAuthVendorName.valueOf(authVendorName);

    OAuthUserProfile oAuthUserProfile =
        authenticationService.oauthLoginStrategy(
            vendorName, memberRegisterRequest.getVendorAccessToken());

    boolean exist =
        memberRepository.existsByAuthVendorMemberId(oAuthUserProfile.getAuthVendorMemberId());
    if (!exist) throw new DuplicateAuthVendorMemberId();

    int authVendorId = authenticationService.GetOAuthVendorIdByName(vendorName);

    Member member =
        memberRegisterRequest.toEntity(
            memberRegisterRequest,
            passwordEncoder,
            authVendorId,
            oAuthUserProfile.getAuthVendorMemberId());

    memberRepository.save(member);

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
