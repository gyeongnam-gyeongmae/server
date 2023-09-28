package megabrain.gyeongnamgyeongmae.domain.chat.domain.repository;

import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
  @Query(
      value =
          "SELECT room FROM ChatRoom room "
              + "JOIN ChatParticipant user ON room.id = user.chatRoom.id "
              + "WHERE user.user.id  = ?1")
  List<ChatRoom> getChatRoomsByParticipantId(Long userId);
}
