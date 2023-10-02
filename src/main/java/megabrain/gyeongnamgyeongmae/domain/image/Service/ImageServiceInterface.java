package megabrain.gyeongnamgyeongmae.domain.image.Service;

import java.io.IOException;
import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.image.dto.UploadType;
import org.springframework.web.multipart.MultipartFile;

public interface ImageServiceInterface {

  void uploadImage(List<MultipartFile> images, UploadType from, Long id) throws IOException;
}
