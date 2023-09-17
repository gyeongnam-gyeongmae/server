package megabrain.gyeongnamgyeongmae.global.advice;

import java.util.Objects;
import megabrain.gyeongnamgyeongmae.domain.authentication.exception.OAuthVendorNotFoundException;
import megabrain.gyeongnamgyeongmae.domain.member.exception.DuplicateAuthVendorMemberId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> validationNotValidException(MethodArgumentNotValidException e) {
    return new ResponseEntity<>(
        Objects.requireNonNull(e.getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(OAuthVendorNotFoundException.class)
  public ResponseEntity<String> oAuthVendorNotFoundException(OAuthVendorNotFoundException error) {
    return new ResponseEntity<>(error.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DuplicateAuthVendorMemberId.class)
  public ResponseEntity<String> duplicateAuthVendorMemberId(DuplicateAuthVendorMemberId error) {
    return new ResponseEntity<>(error.getMessage(), HttpStatus.CONFLICT);
  }
}