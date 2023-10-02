package megabrain.gyeongnamgyeongmae.domain.chat.service;

import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatMessage;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatRoom;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.MessageTypeEnum;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;

public interface ChatMessageServiceInterface {
  List<ChatMessage> getChatMessageByChatRoomId(Long id);

  ChatMessage saveMessage(String content, MessageTypeEnum type, ChatRoom room, User user);
}
