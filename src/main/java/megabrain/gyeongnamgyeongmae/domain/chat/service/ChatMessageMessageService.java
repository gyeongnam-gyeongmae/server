package megabrain.gyeongnamgyeongmae.domain.chat.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item.AuctionItemService;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatMessage;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatRoom;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.MessageTypeEnum;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.repository.ChatMessageRepository;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.repository.ChatRoomRepository;
import megabrain.gyeongnamgyeongmae.domain.chat.dto.RecentChatMessageResponse;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatMessageMessageService implements ChatMessageServiceInterface {

  private final ChatMessageRepository chatMessageRepository;
  private final ChatRoomRepository chatRoomRepository;
  private final AuctionItemService auctionItemService;
  private final RedisMessageBrokerService redisMessageBrokerService;

  @Override
  public List<ChatMessage> getChatMessageByChatRoomId(Long roomId) {
    return chatMessageRepository.getChatMessageByRoomIdOrderByCreatedAtAsc(roomId);
  }

  @Override
  public ChatMessage saveMessage(String content, MessageTypeEnum type, ChatRoom room, User user) {

    ChatMessage sendMessage = ChatMessage.of(content, type, room, user);
    room.addMessage(sendMessage);
    chatRoomRepository.save(room);
    redisMessageBrokerService.sender(RecentChatMessageResponse.of(sendMessage));
    return sendMessage;
  }
}
