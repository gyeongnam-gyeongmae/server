package megabrain.gyeongnamgyeongmae.domain.chat.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.exception.AuctionNotFoundException;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatParticipant;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatRoom;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.repository.ChatParticipantRepository;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.repository.ChatRoomRepository;
import megabrain.gyeongnamgyeongmae.domain.chat.exception.ChatRoomNotFoundException;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService implements ChatRoomServiceInterface {

  private final UserService userService;
  private final AuctionItemRepository auctionItemRepository;
  private final ChatRoomRepository chatRoomRepository;
  private final ChatParticipantRepository chatParticipantRepository;

  @Override
  @Transactional
  public void createChatRoom(Long auctionId, Long sellerId, Long buyerId) {
    AuctionItem auction =
        auctionItemRepository.findById(auctionId).orElseThrow(AuctionNotFoundException::new);
    ChatRoom chatRoom = ChatRoom.of(auction);

    User seller = userService.findUserById(sellerId);
    User buyer = userService.findUserById(buyerId);

    ChatParticipant sellerParticipant = ChatParticipant.from(seller);
    ChatParticipant buyerParticipant = ChatParticipant.from(buyer);

    chatRoom.addParticipants(sellerParticipant);
    chatRoom.addParticipants(buyerParticipant);

    chatRoomRepository.save(chatRoom);
  }

  @Override
  public List<ChatRoom> getJoinChatRoomByUserId(Long userId) {
    return chatRoomRepository.getChatRoomsByParticipantId(userId);
  }

  @Override
  public List<ChatRoom> getChatRoomByAuctionId(Long auctionId) {
    return null;
  }

  @Override
  public ChatRoom getChatRoomById(Long id) {
    return chatRoomRepository.findById(id).orElseThrow(ChatRoomNotFoundException::new);
  }

  @Override
  public Boolean IsUserParticipantInChatRoom(Long userId, ChatRoom chatRoom) {
    return true;
  }
}
