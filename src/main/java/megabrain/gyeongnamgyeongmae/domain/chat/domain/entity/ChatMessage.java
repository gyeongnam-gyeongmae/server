package megabrain.gyeongnamgyeongmae.domain.chat.domain.entity;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.global.BaseTimeEntity;

@Getter
@Entity
@Table(name = "chats")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "chat_id")
  private Long id;

  @Column(name = "content", columnDefinition = "TEXT")
  private String content;

  @Column(name = "message_type")
  @Enumerated(EnumType.STRING)
  private MessageTypeEnum type;

  @ManyToOne(targetEntity = ChatRoom.class)
  @JoinColumn(name = "room_id")
  private ChatRoom room;

  @Column(name = "room_id", insertable = false, updatable = false)
  private Long roomId;

  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "user_id")
  private User user;

  private ChatMessage(String content, MessageTypeEnum type, ChatRoom room, User user) {
    this.content = content;
    this.type = type;
    this.room = room;
    this.user = user;
  }

  public static ChatMessage of(String content, MessageTypeEnum type, ChatRoom room, User user) {
    return new ChatMessage(content, type, room, user);
  }

  public void setRoom(ChatRoom room) {
    this.room = room;
  }
}
