package megabrain.gyeongnamgyeongmae.global.advice;

import megabrain.gyeongnamgyeongmae.domain.authentication.exception.OAuthVendorNotFoundException;
import megabrain.gyeongnamgyeongmae.domain.member.exception.DuplicateAuthVendorMemberId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(OAuthVendorNotFoundException.class)
  public ResponseEntity<HttpStatus> oAuthVendorNotFoundException(
      OAuthVendorNotFoundException error) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }

  @ExceptionHandler(DuplicateAuthVendorMemberId.class)
  public ResponseEntity<HttpStatus> duplicateAuthVendorMemberId(DuplicateAuthVendorMemberId error) {
    return ResponseEntity.status(HttpStatus.CONFLICT).build();
  }
}
