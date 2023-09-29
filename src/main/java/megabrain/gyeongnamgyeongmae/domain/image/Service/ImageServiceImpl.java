package megabrain.gyeongnamgyeongmae.domain.image.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Comment;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item.AuctionItemService;
import megabrain.gyeongnamgyeongmae.domain.image.domain.entity.Image;
import megabrain.gyeongnamgyeongmae.domain.image.domain.repository.ImageRepository;
import megabrain.gyeongnamgyeongmae.domain.image.dto.FileType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

  private final ImageRepository imageRepository;
  private final AwsS3Service awsS3Service;
  private final AuctionItemService auctionItemService;

  @Override
  public void uploadImage(List<MultipartFile> images, String from, Long id) throws IOException {
    String whereFrom = checkImageUploadFind(from);
    AuctionItem auctionItem = null;
    Comment comment = null;
    if (whereFrom.equals("AuctionItem")) {
      auctionItem = checkIsRealIdAuctionItem(id);
    }
//    if (whereFrom.equals("Comment")) {
//      comment = checkIsRealIdComment(id);
//    }
    upload(images, whereFrom, auctionItem, comment);
  }

  @Override
  public List<Image> findImageByAuctionItemId(Long id) {
    return imageRepository.findImageByAuctionItemId(id);
  }

//  @Override
//  public Image findFirstImageByAuctionItemId(Long id) {
//    return imageRepository.findFirstImageByAuctionItemId(id);
//  }

  @Override
  public List<String> findImageByAuctionItemIdBackUrls(Long id) {
    List<Image> images = findImageByAuctionItemId(id);
    if (images == null || images.isEmpty()) {
      return Collections.emptyList();
    }
    return images.stream().map(Image::getImageUrl).collect(Collectors.toList());
  }

  private AuctionItem checkIsRealIdAuctionItem(Long id) {
    return auctionItemService.findAuctionItemById(id);
  }

  private void upload(
      List<MultipartFile> images, String from, AuctionItem auctionItem, Comment comment)
      throws IOException {
    for (MultipartFile file : images) {
      String originalFilename = file.getOriginalFilename();

      if (originalFilename == null) {
        throw new RuntimeException("파일 확장자 없음");
      }

      String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

      if (!FileType.isValid(fileExtension)) {
        throw new RuntimeException("이미지 파일 아님");
      }

      String fileName = uploadFileName(from, fileExtension);
      awsS3Service.upload(file, fileName);

      String fileShow = "https://d231cnlxdxmjew.cloudfront.net/" + fileName;
      Image image = Image.builder().imageFrom(from).name(originalFilename).url(fileShow).build();
      image.setAuctionItem(auctionItem);
      image.setComment(comment);
      imageRepository.save(image);
    }
  }

  private String uploadFileName(String from, String fileExtension) {
    return from + "/" + (UUID.randomUUID().toString().replace("-", "") + "." + fileExtension);
  }

  private String checkImageUploadFind(String from) {
    if (from.equals("Profile")) {
      return "Profile";
    }
    if (from.equals("AuctionItem")) {
      return "AuctionItem";
    }
    if (from.equals("Comment")) {
      return "Comment";
    }
    throw new RuntimeException("이미지 파라미터 잘못");
  }
}
