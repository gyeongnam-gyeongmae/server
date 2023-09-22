package megabrain.gyeongnamgyeongmae.domain.image.Service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    void uploadImage(List<MultipartFile> images, String from, Long id) throws IOException;

}
