package megabrain.gyeongnamgyeongmae.domain.chat.controller;

import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.authentication.service.AuthenticationService;
import megabrain.gyeongnamgyeongmae.domain.chat.domain.entity.ChatRoom;
import megabrain.gyeongnamgyeongmae.domain.chat.service.ChatRoomServiceInterface;
import megabrain.gyeongnamgyeongmae.global.anotation.LoginRequired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-rooms")
public class ChatRoomController {

  private final ChatRoomServiceInterface chatRoomService;
  private final AuthenticationService authenticationService;

  @PostMapping("/{auction-id}")
  public ResponseEntity<HttpStatus> createChatRoom(
      @Parameter(name = "auction-id", example = "1", required = true, description = "종료된 경매 아이디")
          @PathVariable("auction-id")
          Long auctionId,
      @Parameter Long sellerId,
      @Parameter Long buyerId) {

    this.chatRoomService.createChatRoom(auctionId, sellerId, buyerId);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @LoginRequired
  @GetMapping
  public ResponseEntity<List<ChatRoom>> getMyChatRoom() {

    List<ChatRoom> chatRooms =
        this.chatRoomService.getJoinChatRoomByUserId(
            this.authenticationService.getLoginUser().getId());

    return ResponseEntity.ok(chatRooms);
  }
}
