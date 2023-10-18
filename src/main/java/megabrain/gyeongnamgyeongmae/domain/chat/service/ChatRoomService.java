package megabrain.gyeongnamgyeongmae.domain.chat.service;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionStatus;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.AuctionItemRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.repostiory.SseEmitterRepository;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.exception.AuctionNotFoundException;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatParticipant;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatParticipantId;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatRoom;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.repository.ChatParticipantRepository;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.repository.ChatRoomRepository;
import megabrain.gyeongnamgyeongmae.domain.chat.exception.ChatRoomNotFoundException;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserServiceInterface;
import megabrain.gyeongnamgyeongmae.global.config.RedisSseSubscriber;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class ChatRoomService implements ChatRoomServiceInterface {

  private final UserServiceInterface userService;
  private final AuctionItemRepository auctionItemRepository;
  private final ChatRoomRepository chatRoomRepository;
  private final ChatParticipantRepository chatParticipantRepository;
  private final RedisSseSubscriber redisSubscriber;
  private final SseEmitterRepository sseEmitterRepository;
  public static final String CHAT_ROOM_STATE = "CHAT_ROOM_STATE";
  private final Long DEFAULT_TIMEOUT = 24L * 60 * 60 * 1000; // 1일

  @Override
  @Transactional
  public void createChatRoom(Long auctionId, Long sellerId, Long buyerId) {
    AuctionItem auction =
        auctionItemRepository.findById(auctionId).orElseThrow(AuctionNotFoundException::new);
    ChatRoom chatRoom = ChatRoom.of(auction);

    User seller = userService.findUserById(sellerId);
    User buyer = userService.findUserById(buyerId);

    ChatParticipant sellerParticipant = ChatParticipant.of(seller, chatRoom);
    ChatParticipant buyerParticipant = ChatParticipant.of(buyer, chatRoom);

    chatRoom.addParticipants(sellerParticipant);
    chatRoom.addParticipants(buyerParticipant);

    auction.setBuyer(buyer);
    auction.setStatus(AuctionStatus.CLOSED);
    chatRoomRepository.save(chatRoom);
    auctionItemRepository.save(auction);
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
  public Boolean isUserParticipantInChatRoom(Long userId, ChatRoom chatRoom) {
    return true;
  }

  @Override
  public SseEmitter subscribeChatRoomState(Long id) {
    String channelName = CHAT_ROOM_STATE + id;
    redisSubscriber.createChannel(channelName);
    SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
    sseEmitter.onTimeout(() -> sseEmitterRepository.delete(channelName, sseEmitter));

    sseEmitterRepository.save(channelName, sseEmitter);

    try {
      sseEmitter.send(SseEmitter.event().data("INIT"));
    } catch (IOException e) {
      sseEmitterRepository.delete(channelName, sseEmitter);
    }
    return sseEmitter;
  }

  @Override
  public void completeChatRoom(Long chatRoomId, Long userId) {
    ChatParticipant chatParticipant =
        chatParticipantRepository
            .findById(ChatParticipantId.from(chatRoomId, userId))
            .orElseThrow(RuntimeException::new);
    chatParticipant.setIsCompleted(true);

    long chatParticipantCount =
        chatParticipantRepository.countCompletedChatParticipants(chatRoomId);

    List<SseEmitter> sseEmitters =
        sseEmitterRepository.findAllByChannelName(CHAT_ROOM_STATE + chatRoomId);
    if (sseEmitters == null) return;
    for (SseEmitter sseEmitter : sseEmitters) {
      try {
        sseEmitter.send(SseEmitter.event().data("COMPLETE:" + userId));
        if (chatParticipantCount == 2) {
          sseEmitter.send(SseEmitter.event().data("GONE"));
          sseEmitter.complete();
        }
      } catch (IOException e) {
        sseEmitterRepository.delete(CHAT_ROOM_STATE + chatRoomId, sseEmitter);
      }
    }
  }
}
