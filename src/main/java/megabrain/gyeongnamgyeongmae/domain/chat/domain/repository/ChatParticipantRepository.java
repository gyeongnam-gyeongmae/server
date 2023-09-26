package megabrain.gyeongnamgyeongmae.domain.chat.domain.repository;

import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatParticipant;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatParticipantRepository
    extends JpaRepository<ChatParticipant, ChatParticipantId> {}
