package megabrain.gyeongnamgyeongmae.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatRoom;
import megabrain.gyeongnamgyeongmae.domain.chat.dto.ChatMessageSendRequest;
import megabrain.gyeongnamgyeongmae.domain.chat.service.ChatMessageServiceInterface;
import megabrain.gyeongnamgyeongmae.domain.chat.service.ChatRoomServiceInterface;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserServiceInterface;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatSocketController {
  private final UserServiceInterface userService;
  private final ChatMessageServiceInterface chatMessageService;
  private final ChatRoomServiceInterface chatRoomService;

  /*
    /subscribe/chat-rooms/{id}  구독자는 해당 URI를 구독하여 메시지를 받습니다.
    /publish/chat-rooms/{id}    송신자는 해당 URI에 메시지를 보내면 해당 채팅방를 구독 중인 모든 구독자에게 메시지를 전달합니다.
  */

  @MessageMapping("/chat-rooms/{id}")
  public void sendMessage(@DestinationVariable Long id, ChatMessageSendRequest message) {
    User user = userService.findUserById(message.getUserId());

    ChatRoom chatRoom = chatRoomService.getChatRoomById(id);
    if (chatRoom == null) return;

    chatRoomService.IsUserParticipantInChatRoom(user.getId(), chatRoom);

    chatMessageService.saveMessage(message.getContent(), message.getMessageType(), chatRoom, user);
  }
}
