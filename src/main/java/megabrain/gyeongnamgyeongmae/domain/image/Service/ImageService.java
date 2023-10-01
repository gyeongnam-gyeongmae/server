package megabrain.gyeongnamgyeongmae.domain.image.Service;

import java.io.IOException;
import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.image.domain.entity.Image;
import megabrain.gyeongnamgyeongmae.domain.image.domain.entity.UploadType;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

  void uploadImage(List<MultipartFile> images, UploadType from, Long id) throws IOException;

  List<Image> findImageByAuctionItemId(Long id);

  List<String> findImageByAuctionItemIdBackUrls(Long id);

//  Image findFirstImageByAuctionItemId(Long id);
}
