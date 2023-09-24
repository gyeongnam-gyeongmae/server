package megabrain.gyeongnamgyeongmae.domain.chat.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatMessage;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatRoom;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.MessageTypeEnum;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.repository.ChatMessageRepository;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageMessageService implements ChatMessageServiceInterface {

  private final ChatMessageRepository chatMessageRepository;

  @Override
  public List<ChatMessage> getChatMessageByChatRoomId(Long roomId) {
    return chatMessageRepository.getChatMessageByRoomIdOrderByCreatedAtAsc(roomId);
  }

  @Override
  public void saveMessage(String content, MessageTypeEnum type, ChatRoom room, User user) {
    chatMessageRepository.save(ChatMessage.of(content, type, room, user));
  }
}
