package megabrain.gyeongnamgyeongmae.domain.authentication.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.authentication.domain.entity.OAuthVendorName;
import megabrain.gyeongnamgyeongmae.domain.authentication.dto.PhoneAuthenticationRequest;
import megabrain.gyeongnamgyeongmae.domain.authentication.dto.UserRegisterRequest;
import megabrain.gyeongnamgyeongmae.domain.authentication.service.AuthenticationServiceInterface;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserService;
import megabrain.gyeongnamgyeongmae.global.anotation.LoginRequired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "인증 기능", description = "회원 인증 API")
@RequestMapping("api/authentications")
public class AuthenticationController {
  private final AuthenticationServiceInterface authenticationService;
  private final UserService userService;

  @PostMapping("register/{auth-vendor}")
  @Operation(summary = "회원가입/로그인 요청", description = "회원의 OAuth 회원가입/로그인을 요청합니다.")
  @ResponseStatus(value = HttpStatus.CREATED, reason = "회원가입/로그인 성공, 세션 키를 쿠키에 담아 반환합니다.")
  public ResponseEntity<HttpStatus> userRegisterWithOAuthVendor(
      @Parameter(
              name = "auth-vendor",
              example = "KAKAO",
              required = true,
              description = "지원중인 OAuth2 제공사(KAKAO)")
          @PathVariable("auth-vendor")
          OAuthVendorName authVendorName,
      @RequestBody @Valid UserRegisterRequest userRegisterRequest) {

    User user =
        this.authenticationService
            .oauthLoginStrategy(authVendorName, userRegisterRequest.getVendorAccessToken())
            .toUserEntity();

    Long userId = this.userService.getIdByAuthVendorUserId(user.getAuthVendorUserId());

    if (userId == null) {
      this.userService.registerUser(user);
      userId = user.getId();
    }
    this.authenticationService.login(userId);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("{id}/session")
  @Operation(summary = "유저 아이디로 세션 획득(테스트용)", description = "유저 아이디로 세션 획득(테스트용)")
  public ResponseEntity<HttpStatus> getSession(@PathVariable("id") Long userId) {

    this.authenticationService.login(userId);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @LoginRequired
  @PostMapping("logout")
  @Operation(summary = "로그아웃 요청", description = "로그아웃을 요청합니다.")
  public ResponseEntity<HttpStatus> logout() {
    this.authenticationService.logout();
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @LoginRequired
  @GetMapping("profile")
  @Operation(
      summary = "프로필 조회 요청",
      description = "쿠키에 존재하는 세션 키를 사용하여 현재 로그인되어있는 회원의 프로필 정보를 조회합니다.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "회원 정보 반환"),
        @ApiResponse(responseCode = "401", description = "세션 로그인 필요"),
      })
  public ResponseEntity<User> getMyProfile() {
    User logedInUser = this.authenticationService.getLoginUser();
    return new ResponseEntity<>(logedInUser, HttpStatus.OK);
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
      @RequestBody @Valid PhoneAuthenticationRequest phoneAuthenticationRequest) {
    String phoneAuthenticationCode = authenticationService.generatePhoneAuthenticationCode();

    authenticationService.sendPhoneAuthenticationCode(
        phoneAuthenticationRequest.getPhoneNumber(), phoneAuthenticationCode);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
