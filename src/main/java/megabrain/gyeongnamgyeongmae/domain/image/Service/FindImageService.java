package megabrain.gyeongnamgyeongmae.domain.image.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.image.domain.entity.Image;
import megabrain.gyeongnamgyeongmae.domain.image.domain.repository.ImageRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindImageService implements FindImageServiceInterface {

  private final ImageRepository imageRepository;

  @Override
  public List<String> findImageByAuctionItemIdBackUrls(Long id) {
    List<Image> images = findImageByAuctionItemId(id);
    if (images == null || images.isEmpty()) {
      return Collections.emptyList();
    }
    return images.stream().map(Image::getImageUrl).collect(Collectors.toList());
  }

  private List<Image> findImageByAuctionItemId(Long id) {
    return imageRepository.findImageByAuctionItemId(id);
  }

  @Override
  public Image findFirstImageByAuctionItemId(Long id) {
    return imageRepository.findFirstImageByAuctionItemId(id);
  }


  @Override
  public Image findFirstImageByUserId(Long id) {
    return imageRepository.findFirstImageByUserId(id);
  }
}
