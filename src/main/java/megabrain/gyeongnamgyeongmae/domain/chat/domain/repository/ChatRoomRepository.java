package megabrain.gyeongnamgyeongmae.domain.chat.domain.repository;

import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
  @Query(
      value =
          "SELECT room FROM ChatRoom room "
              + "JOIN FETCH ChatMessage message ON room.id = message.room.id "
              + "JOIN ChatParticipant user ON room.id = user.chatRoom.id "
              + "WHERE message.createdAt = (SELECT MAX(message.createdAt) FROM ChatMessage message WHERE message.room.id = room.id) AND user.user.id  = :userId "
              + "ORDER BY message.createdAt DESC")
  List<ChatRoom> getChatRoomsByParticipantId(@Param("userId") Long userId);
}
