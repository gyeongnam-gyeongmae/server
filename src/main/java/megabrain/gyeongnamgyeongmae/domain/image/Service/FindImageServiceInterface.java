package megabrain.gyeongnamgyeongmae.domain.image.Service;

import megabrain.gyeongnamgyeongmae.domain.image.domain.entity.Image;

import java.util.List;

public interface FindImageServiceInterface {

  List<String> findImageByAuctionItemIdBackUrls(Long id);

  Image findFirstImageByAuctionItemId(Long id);
}
