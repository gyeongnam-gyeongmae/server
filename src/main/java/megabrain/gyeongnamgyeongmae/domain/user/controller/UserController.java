package megabrain.gyeongnamgyeongmae.domain.user.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.authentication.service.AuthenticationServiceInterface;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserAddressCreateRequest;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserService;
import megabrain.gyeongnamgyeongmae.global.anotation.LoginRequired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

  private final UserService userService;
  private final AuthenticationServiceInterface authenticationService;

  @PostMapping("address")
  @LoginRequired
  @Operation(
      summary = "주소 등록",
      description = "카카오 API를 활용하여 좌표를 주소로 변환하여 등록합니다.",
      externalDocs =
          @ExternalDocumentation(
              description = "좌표를 주소로 변환하는 방법은 다음 링크를 참고하세요.",
              url = "https://developers.kakao.com/docs/latest/ko/local/dev-guide#coord-to-address"))
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "주소 등록 성공"),
        @ApiResponse(responseCode = "400", description = "좌표가 올바르지 않음"),
        @ApiResponse(responseCode = "401", description = "로그인 필요"),
        @ApiResponse(responseCode = "500", description = "카카오 서버에서 좌표를 주소로 변환하는데 실패함")
      })
  public ResponseEntity<HttpStatus> setAddress(
      @RequestBody @NotEmpty UserAddressCreateRequest userAddressCreateRequest) {

    userService.setAddress(
        authenticationService.getLoginUser().getId(),
        userAddressCreateRequest.getLatitude(),
        userAddressCreateRequest.getLongitude());

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
