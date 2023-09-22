package megabrain.gyeongnamgyeongmae.domain.chat.domain.entity;

import javax.persistence.*;
import lombok.Getter;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;

@Getter
@Entity
@Table(name = "chat_participants")
@IdClass(ChatParticipantId.class)
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
}
