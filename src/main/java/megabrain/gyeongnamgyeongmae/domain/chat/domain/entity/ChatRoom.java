package megabrain.gyeongnamgyeongmae.domain.chat.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.global.BaseTimeEntity;

@Getter
@Entity
@Table(name = "chat_rooms")
@NoArgsConstructor
public class ChatRoom extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chat_room_id")
  private Long id;

  @ManyToOne(targetEntity = AuctionItem.class)
  @JoinColumn(name = "auction_id")
  private AuctionItem auction;

  @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore // 순환참조를 무시하기 위하여 사용
  private List<ChatParticipant> participants = new ArrayList<>();

  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonIgnore // 순환참조를 무시하기 위하여 사용
  private List<ChatMessage> messages = new ArrayList<>();

  private ChatRoom(AuctionItem auction) {
    this.auction = auction;
  }

  // TODO: 기존에 채팅방에 참여한 경우 재대입 시 불일치 문제 발생
  public void addParticipants(ChatParticipant user) {
    this.participants.add(user);
    if (user.getChatRoom() != this) user.setRoom(this);
  }

  public static ChatRoom of(AuctionItem auction) {
    return new ChatRoom(auction);
  }

  public void addMessage(ChatMessage message) {
    this.messages.add(message);
    if (message.getRoom() != this) message.setRoom(this);
  }
}
