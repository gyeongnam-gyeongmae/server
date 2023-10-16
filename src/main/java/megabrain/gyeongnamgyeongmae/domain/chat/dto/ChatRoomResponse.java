package megabrain.gyeongnamgyeongmae.domain.chat.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatRoom;

@Getter
@Setter
@AllArgsConstructor
public class ChatRoomResponse {
  private Long id;
  private AuctionItem auction;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Boolean isCompleted;

  private ChatRoomResponse(ChatRoom chatRoom) {
    this.id = chatRoom.getId();
    this.auction = chatRoom.getAuction();
    this.createdAt = chatRoom.getCreatedAt();
    this.updatedAt = chatRoom.getUpdatedAt();
    this.isCompleted = chatRoom.getAuction().isClosed();
  }

  public static ChatRoomResponse of(ChatRoom chatRoom) {
    return new ChatRoomResponse(chatRoom);
  }
}
