package megabrain.gyeongnamgyeongmae.domain.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.authentication.service.AuthenticationServiceInterface;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatRoom;
import megabrain.gyeongnamgyeongmae.domain.chat.dto.ChatRoomResponse;
import megabrain.gyeongnamgyeongmae.domain.chat.service.ChatRoomServiceInterface;
import megabrain.gyeongnamgyeongmae.global.anotation.LoginRequired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-rooms")
@Tag(name = "채팅 메시지 API", description = "채팅 메시지 API")
public class ChatRoomController {

  private final ChatRoomServiceInterface chatRoomService;
  private final AuthenticationServiceInterface authenticationService;

  @PostMapping("/{auction-id}")
  @Operation(
      summary = "채팅방 생성(테스트 용)",
      description = "테스트 용의 채팅방을 생성합니다. 추후에는 경매가 삭제되면 자동으로 채팅방에 생성되게 구현됩니다.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "채팅방 생성 성공"),
        @ApiResponse(responseCode = "404", description = "구매자 혹은 판매자를 찾을 수 없음"),
      })
  public ResponseEntity<HttpStatus> createChatRoom(
      @Parameter(name = "auction-id", example = "1", required = true, description = "종료된 경매 아이디")
          @PathVariable("auction-id")
          Long auctionId,
      @Parameter Long sellerId,
      @Parameter Long buyerId) {

    this.chatRoomService.createChatRoom(auctionId, sellerId, buyerId);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @LoginRequired
  @Operation(summary = "내가 속해있는 채팅방 조회 (세션 필요 🔑)", description = "내가 속해있는 채팅방을 조회합니다.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "채팅방 조회 성공"),
        @ApiResponse(responseCode = "401", description = "세션 로그인 필요"),
      })
  @GetMapping
  public ResponseEntity<List<ChatRoomResponse>> getMyChatRoom() {

    List<ChatRoom> chatRooms =
        this.chatRoomService.getJoinChatRoomByUserId(
            this.authenticationService.getLoginUser().getId());

    ArrayList<ChatRoomResponse> chatRoomsResponse = new ArrayList<>();
    for (ChatRoom chatRoom : chatRooms) {
      chatRoomsResponse.add(ChatRoomResponse.of(chatRoom));
    }

    return ResponseEntity.ok(chatRoomsResponse);
  }
}
