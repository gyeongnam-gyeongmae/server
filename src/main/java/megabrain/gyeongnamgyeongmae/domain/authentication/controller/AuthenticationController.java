package megabrain.gyeongnamgyeongmae.domain.authentication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthUserProfile;
import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthVendorName;
import megabrain.gyeongnamgyeongmae.domain.authentication.dto.MemberRegisterRequest;
import megabrain.gyeongnamgyeongmae.domain.authentication.dto.PhoneAuthenticationRequest;
import megabrain.gyeongnamgyeongmae.domain.authentication.service.AuthenticationServiceInterface;
import megabrain.gyeongnamgyeongmae.domain.member.domain.entity.Member;
import megabrain.gyeongnamgyeongmae.domain.member.exception.DuplicateAuthVendorMemberId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "인증 기능", description = "회원 인증 API")
@RequestMapping("api/authentications")
public class AuthenticationController {
  private final AuthenticationServiceInterface authenticationService;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("register/{auth-vendor}")
  @Operation(summary = "회원가입 요청", description = "회원의 회원가입을 요청합니다.")
  public ResponseEntity<HttpStatus> memberRegisterWithOAuthVendor(
      @Parameter(
              name = "auth-vendor",
              example = "KAKAO",
              required = true,
              description = "지원중인 OAuth2 제공사(KAKAO)")
          @PathVariable("auth-vendor")
          String authVendorName,
      @RequestBody() @Valid MemberRegisterRequest memberRegisterRequest) {
    authenticationService.isValidateOAuthVendorName(authVendorName);
    OAuthVendorName vendorName = OAuthVendorName.valueOf(authVendorName);

    OAuthUserProfile oAuthUserProfile =
        authenticationService.oauthLoginStrategy(
            vendorName, memberRegisterRequest.getVendorAccessToken());

    boolean isDuplicated =
        authenticationService.isDuplicateAuthVendorMemberId(
            oAuthUserProfile.getAuthVendorMemberId());
    if (!isDuplicated) throw new DuplicateAuthVendorMemberId();

    int authVendorId = authenticationService.GetOAuthVendorIdByName(vendorName);

    Member member =
        MemberRegisterRequest.toEntity(
            memberRegisterRequest,
            passwordEncoder,
            authVendorId,
            oAuthUserProfile.getAuthVendorMemberId());

    authenticationService.saveMember(member);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("phone")
  @Operation(summary = "휴대전화 인증번호 검증", description = "회원가입 전 올바른 휴대전화 인증번호인지 여부를 요청합니다.")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<HttpStatus> checkPhoneAuthenticationCode(
      @Parameter(
              name = "phoneNumber",
              example = "01012345678",
              required = true,
              description = "인증번호를 받은 전화번호(길이 11)")
          @RequestParam
          String phoneNumber,
      @Parameter(name = "code", example = "123456", required = true, description = "인증번호(길이 6)")
          @RequestParam
          String code) {

    boolean exist = authenticationService.isPhoneAuthenticationCodeExist(phoneNumber, code);

    if (!exist) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("phone")
  @Operation(summary = "휴대전화 인증 요청", description = "휴대전화 번호를 통해 인증번호 송신을 요청합니다.")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<HttpStatus> authenticationByMail(
      @RequestBody(
              required = true,
              content =
                  @Content(schema = @Schema(implementation = PhoneAuthenticationRequest.class)))
          @Valid
          PhoneAuthenticationRequest phoneAuthenticationRequest) {
    String phoneAuthenticationCode = authenticationService.generatePhoneAuthenticationCode();

    authenticationService.sendPhoneAuthenticationCode(
        phoneAuthenticationRequest.getPhoneNumber(), phoneAuthenticationCode);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
