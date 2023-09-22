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
  private MessageType type;

  @ManyToOne(targetEntity = ChatRoom.class)
  @JoinColumn(name = "room_id")
  private String room;

  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "user_id")
  private User user;

  public enum MessageType {
    TALK,
    LOCATION,
  }
}
