package megabrain.gyeongnamgyeongmae.domain.image.Service;

import java.io.IOException;
import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.image.domain.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

  void uploadImage(List<MultipartFile> images, String from, Long id) throws IOException;

  List<Image> findImageByAuctionItemId(Long id);

  List<String> findImageByAuctionItemIdBackUrls(Long id);
}
