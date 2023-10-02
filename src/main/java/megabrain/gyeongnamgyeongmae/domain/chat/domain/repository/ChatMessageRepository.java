package megabrain.gyeongnamgyeongmae.domain.chat.domain.repository;

import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
  List<ChatMessage> getChatMessageByRoomIdOrderByCreatedAtDesc(Long roomId);
}
