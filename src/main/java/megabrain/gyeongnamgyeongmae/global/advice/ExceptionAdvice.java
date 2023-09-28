package megabrain.gyeongnamgyeongmae.global.advice;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.authentication.exception.OAuthLoginException;
import megabrain.gyeongnamgyeongmae.domain.authentication.exception.OAuthVendorNotFoundException;
import megabrain.gyeongnamgyeongmae.domain.authentication.exception.UnAuthenticatedException;
import megabrain.gyeongnamgyeongmae.domain.chat.exception.ChatRoomNotFoundException;
import megabrain.gyeongnamgyeongmae.domain.chat.exception.UserNotParticipantInChatRoomException;
import megabrain.gyeongnamgyeongmae.domain.chat.service.RedisMessageBrokerService;
import megabrain.gyeongnamgyeongmae.domain.user.exception.DuplicateAuthVendorUserId;
import megabrain.gyeongnamgyeongmae.domain.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {
  private final RedisMessageBrokerService redisMessageBrokerService;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> validationNotValidException(MethodArgumentNotValidException e) {
    return new ResponseEntity<>(
        Objects.requireNonNull(e.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(OAuthVendorNotFoundException.class)
  public ResponseEntity<String> oAuthVendorNotFoundException(OAuthVendorNotFoundException error) {
    return new ResponseEntity<>(error.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<String> userNotFoundException(UserNotFoundException error) {
    return new ResponseEntity<>(error.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DuplicateAuthVendorUserId.class)
  public ResponseEntity<String> duplicateAuthVendorUserId(DuplicateAuthVendorUserId error) {
    return new ResponseEntity<>(error.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(UnAuthenticatedException.class)
  public ResponseEntity<HttpStatus> unAuthorizedException() {
    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(OAuthLoginException.class)
  public ResponseEntity<String> oAuthLoginException(OAuthLoginException error) {
    return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ChatRoomNotFoundException.class)
  public ResponseEntity<String> chatRoomNotFoundException(ChatRoomNotFoundException error) {
    return new ResponseEntity<>(error.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UserNotParticipantInChatRoomException.class)
  public ResponseEntity<String> userNotParticipantInChatRoomException(
      UserNotParticipantInChatRoomException error) {
    return new ResponseEntity<>(error.getMessage(), HttpStatus.FORBIDDEN);
  }

  // 소켓 메세지 에러
  @MessageExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<String> messageUserNotFoundException(UserNotFoundException e) {
    redisMessageBrokerService.throwError(e);
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
