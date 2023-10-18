package megabrain.gyeongnamgyeongmae.domain.chat.domain.repository;

import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatParticipant;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatParticipantRepository
    extends JpaRepository<ChatParticipant, ChatParticipantId> {
  @Query(
      value =
          "select count(c) from ChatParticipant c where c.chatRoom.id = ?1 and c.isCompleted = true")
  long countCompletedChatParticipants(@Param("chatRoomId") Long chatRoomId);
}
