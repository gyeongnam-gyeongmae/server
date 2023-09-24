package megabrain.gyeongnamgyeongmae.domain.chat.controller;

import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.authentication.service.AuthenticationServiceInterface;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatMessage;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatRoom;
import megabrain.gyeongnamgyeongmae.domain.chat.service.ChatMessageServiceInterface;
import megabrain.gyeongnamgyeongmae.domain.chat.service.ChatRoomServiceInterface;
import megabrain.gyeongnamgyeongmae.global.anotation.LoginRequired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/chat-rooms")
public class ChatMessageController {
  private final AuthenticationServiceInterface authenticationService;
  private final ChatMessageServiceInterface chatMessageService;
  private final ChatRoomServiceInterface chatRoomService;

  @LoginRequired
  @GetMapping("/{chat-room-id}/messages")
  public ResponseEntity<List<ChatMessage>> getChatMessageInChatRoom(
      @Parameter(name = "chat-room-id", example = "1", required = true, description = "채팅방 아이디")
          @PathVariable("chat-room-id")
          Long id) {

    ChatRoom chatRoom = chatRoomService.getChatRoomById(id);
    if (chatRoom == null) return ResponseEntity.notFound().build();

    Boolean isParticipant =
        chatRoomService.IsUserParticipantInChatRoom(
            authenticationService.getLoginUser().getId(), chatRoom);
    if (!isParticipant) return ResponseEntity.badRequest().build();

    List<ChatMessage> chatMessages =
        chatMessageService.getChatMessageByChatRoomId(chatRoom.getId());

    return ResponseEntity.ok(chatMessages);
  }
}
