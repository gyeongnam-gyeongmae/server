package megabrain.gyeongnamgyeongmae.domain.chat.service;

import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatRoom;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ChatRoomServiceInterface {
  void createChatRoom(Long auctionId, Long sellerId, Long buyerId);

  List<ChatRoom> getJoinChatRoomByUserId(Long userId);

  List<ChatRoom> getChatRoomByAuctionId(Long auctionId);

  ChatRoom getChatRoomById(Long id);

  Boolean isUserParticipantInChatRoom(Long userId, ChatRoom chatRoom);

  SseEmitter subscribeChatRoomState(Long id);

  void completeChatRoom(Long chatRoomId, Long userId);
}
