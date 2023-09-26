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
  @ManyToOne(targetEntity = ChatRoom.class)
  @JoinColumn(name = "chat_room_id")
  private ChatRoom chatRoom;

  @Id
  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "user_id")
  private User user;

  private ChatParticipant(User user) {
    this.user = user;
  }

  private ChatParticipant(ChatRoom chatRoom, User user) {
    this.chatRoom = chatRoom;
    this.user = user;
  }

  public static ChatParticipant of(User user, ChatRoom chatRoom) {
    return new ChatParticipant(chatRoom, user);
  }

  public static ChatParticipant from(User user) {
    return new ChatParticipant(user);
  }
}
