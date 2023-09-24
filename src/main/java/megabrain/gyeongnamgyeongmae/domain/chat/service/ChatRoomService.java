package megabrain.gyeongnamgyeongmae.domain.chat.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.exception.AuctionNotFoundException;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatRoom;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.repository.ChatRoomRepository;
import megabrain.gyeongnamgyeongmae.domain.chat.exception.ChatRoomNotFoundException;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.domain.repository.UserRepository;
import megabrain.gyeongnamgyeongmae.domain.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService implements ChatRoomServiceInterface {

  private final UserRepository userRepository;
  private final AuctionItemRepository auctionItemRepository;
  private final ChatRoomRepository chatRoomRepository;

  @Override
  @Transactional
  public void createChatRoom(Long auctionId, Long sellerId, Long buyerId) {
    AuctionItem auction =
        auctionItemRepository.findById(auctionId).orElseThrow(AuctionNotFoundException::new);
    User seller = userRepository.findById(sellerId).orElseThrow(UserNotFoundException::new);
    User buyer = userRepository.findById(buyerId).orElseThrow(UserNotFoundException::new);

    chatRoomRepository.save(ChatRoom.of(auction, seller, buyer));
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
