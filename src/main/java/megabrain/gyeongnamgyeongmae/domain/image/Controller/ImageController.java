package megabrain.gyeongnamgyeongmae.domain.image.Controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.image.Service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/{from}/{id}/images/")
public class ImageController {

    private final ImageService imageService;

    @PostMapping(value = "/upload", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    @Operation(summary = "이미지", description = "이미지 업로드")
    public ResponseEntity<HttpStatus> uploadImage(
            @RequestPart("file") List<MultipartFile> files, @PathVariable String from, @PathVariable Long id) throws IOException {
        imageService.uploadImage(files, from, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
