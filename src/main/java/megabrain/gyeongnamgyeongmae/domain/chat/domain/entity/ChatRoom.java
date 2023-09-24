package megabrain.gyeongnamgyeongmae.domain.chat.domain.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
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

  @Column(name = "auction_id", insertable = false, updatable = false)
  private Long auctionId;

  @ManyToOne(targetEntity = AuctionItem.class)
  @JoinColumn(name = "auction_id")
  private AuctionItem auction;

  @OneToMany(mappedBy = "chatRoom")
  private List<ChatParticipant> participants = new ArrayList<>();

  private ChatRoom(AuctionItem auction, User seller, User buyer) {
    this.auction = auction;
    List<ChatParticipant> participants = new ArrayList<>();
    participants.add(ChatParticipant.of(this, seller));
    participants.add(ChatParticipant.of(this, buyer));
    this.participants = participants;
  }

  public static ChatRoom of(AuctionItem auction, User seller, User buyer) {
    return new ChatRoom(auction, seller, buyer);
  }
}
