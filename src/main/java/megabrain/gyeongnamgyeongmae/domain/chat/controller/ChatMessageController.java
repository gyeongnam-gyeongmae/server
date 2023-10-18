package megabrain.gyeongnamgyeongmae.domain.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.authentication.service.AuthenticationServiceInterface;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatMessage;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatRoom;
import megabrain.gyeongnamgyeongmae.domain.chat.exception.ChatRoomNotFoundException;
import megabrain.gyeongnamgyeongmae.domain.chat.exception.UserNotParticipantInChatRoomException;
import megabrain.gyeongnamgyeongmae.domain.chat.service.ChatMessageServiceInterface;
import megabrain.gyeongnamgyeongmae.domain.chat.service.ChatRoomServiceInterface;
import megabrain.gyeongnamgyeongmae.global.anotation.LoginRequired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat-rooms")
@Tag(name = "채팅 메시지 API", description = "채팅 메시지 API")
public class ChatMessageController {
  private final AuthenticationServiceInterface authenticationService;
  private final ChatMessageServiceInterface chatMessageService;
  private final ChatRoomServiceInterface chatRoomService;

  @LoginRequired
  @GetMapping("/{chat-room-id}/messages")
  @Operation(summary = "채팅방의 메시지 조회 (세션 필요 🔑)", description = "내가 속해있는 채팅방의 메시지를 조회합니다.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "메시지 조회 성공"),
        @ApiResponse(responseCode = "401", description = "세션 로그인 필요"),
        @ApiResponse(responseCode = "403", description = "채팅방 참여자가 아님"),
        @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없음"),
      })
  public ResponseEntity<List<ChatMessage>> getChatMessageInChatRoom(
      @Parameter(name = "chat-room-id", example = "1", required = true, description = "채팅방 아이디")
          @PathVariable("chat-room-id")
          Long id) {

    ChatRoom chatRoom = chatRoomService.getChatRoomById(id);
    if (chatRoom == null) throw new ChatRoomNotFoundException();

    Boolean isParticipant =
        chatRoomService.isUserParticipantInChatRoom(
            authenticationService.getLoginUser().getId(), chatRoom);
    if (!isParticipant) throw new UserNotParticipantInChatRoomException();

    List<ChatMessage> chatMessages =
        chatMessageService.getChatMessageByChatRoomId(chatRoom.getId());

    return ResponseEntity.ok(chatMessages);
  }
}
