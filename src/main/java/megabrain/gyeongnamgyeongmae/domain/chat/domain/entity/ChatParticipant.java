package megabrain.gyeongnamgyeongmae.domain.chat.domain.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;

@Getter
@Entity
@Table(name = "chat_participants")
@IdClass(ChatParticipantId.class)
@NoArgsConstructor
public class ChatParticipant {

  @Id
  @Column(name = "chat_room_id", insertable = false, updatable = false)
  private Long chatRoomId;

  @Id
  @Column(name = "user_id", insertable = false, updatable = false)
  private Long userId;

  @ManyToOne(targetEntity = ChatRoom.class)
  @JoinColumn(name = "chat_room_id")
  private ChatRoom chatRoom;

  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "user_id")
  private User user;

  private ChatParticipant(ChatRoom chatRoom, User user) {
    this.chatRoom = chatRoom;
    this.user = user;
  }

  public static ChatParticipant of(ChatRoom chatRoom, User user) {
    return new ChatParticipant(chatRoom, user);
  }
}
