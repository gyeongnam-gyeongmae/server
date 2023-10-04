package megabrain.gyeongnamgyeongmae.domain.user.controller;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.authentication.service.AuthenticationServiceInterface;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.Address;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserAddressCreateRequest;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserUpdateRequest;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserServiceInterface;
import megabrain.gyeongnamgyeongmae.global.anotation.LoginRequired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

  private final UserServiceInterface userService;
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
        @ApiResponse(responseCode = "200", description = "주소 등록 성공"),
        @ApiResponse(responseCode = "400", description = "좌표가 올바르지 않음"),
        @ApiResponse(responseCode = "401", description = "로그인 필요"),
        @ApiResponse(responseCode = "500", description = "카카오 서버에서 좌표를 주소로 변환하는데 실패함")
      })
  public ResponseEntity<Address> setAddress(
      @RequestBody @NotEmpty UserAddressCreateRequest userAddressCreateRequest) {

    Address address =
        userService.getAddressByCoordinate(
            userAddressCreateRequest.getLatitude(), userAddressCreateRequest.getLongitude());

    userService.setAddress(authenticationService.getLoginUser().getId(), address);
    return ResponseEntity.ok(address);
  }

  @Operation(summary = "유저 전체조회", description = "유저 모두를 조회합니다")
  @GetMapping("")
  public ResponseEntity<List<User>> findAllUser() {
    List<User> users = userService.findAllUser();
    return ResponseEntity.ok(users);
  }

  @PutMapping()
  @Operation(summary = "유저 업데이트", description = "유저 정보를 업데이트합니다.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "업데이트 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 프로퍼티"),
        @ApiResponse(responseCode = "401", description = "세션 인증 실패")
      })
  @LoginRequired
  public ResponseEntity<HttpStatus> updateUserProfile(
      @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
    User logedIduser = authenticationService.getLoginUser();
    userService.updateUser(logedIduser, userUpdateRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
