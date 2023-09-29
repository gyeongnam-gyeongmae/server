package megabrain.gyeongnamgyeongmae.domain.image.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.image.Service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/{from}/{id}/images/")
public class ImageController {

  private final ImageService imageService;

  @PostMapping(
      value = "/upload",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  @Operation(summary = "이미지 업로드 ", description = "이미지 업로드, from : AuctionItem, id : AuctionItem id 를 입력하시면 됩니다.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "이미지 업로드 성공"),
        @ApiResponse(responseCode = "404", description = "이미지 업로드 실패"),
      })
  public ResponseEntity<HttpStatus> uploadImage(
      @RequestPart("file") List<MultipartFile> files,
      @PathVariable String from,
      @PathVariable Long id)
      throws IOException {
    imageService.uploadImage(files, from, id);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
