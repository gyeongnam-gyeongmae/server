package megabrain.gyeongnamgyeongmae.domain.chat.service;

import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatRoom;

public interface ChatRoomServiceInterface {
  void createChatRoom(Long auctionId, Long sellerId, Long buyerId);

  List<ChatRoom> getJoinChatRoomByUserId(Long userId);

  List<ChatRoom> getChatRoomByAuctionId(Long auctionId);

  ChatRoom getChatRoomById(Long id);

  Boolean IsUserParticipantInChatRoom(Long userId, ChatRoom chatRoom);
}
