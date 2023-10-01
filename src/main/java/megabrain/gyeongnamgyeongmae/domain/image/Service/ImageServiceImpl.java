package megabrain.gyeongnamgyeongmae.domain.image.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.Comment;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item.AuctionItemService;
import megabrain.gyeongnamgyeongmae.domain.image.domain.entity.Image;
import megabrain.gyeongnamgyeongmae.domain.image.domain.repository.ImageRepository;
import megabrain.gyeongnamgyeongmae.domain.image.dto.FileType;
import megabrain.gyeongnamgyeongmae.domain.image.dto.UploadType;
import megabrain.gyeongnamgyeongmae.domain.image.exception.ImageTypeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

  private final ImageRepository imageRepository;
  private final AwsS3Service awsS3Service;
  private final AuctionItemService auctionItemService;

  @Override
  @Transactional
  public void uploadImage(List<MultipartFile> images, UploadType from, Long id) throws IOException {

    switch (from) {
      case AuctionItem:
        upload(images, from.name(), checkIsRealIdAuctionItem(id), null);
        break;
      default:
        throw new ImageTypeException("업로드 타입이 잘못됬습니다 : " + from.name());
    }
  }

  private AuctionItem checkIsRealIdAuctionItem(Long id) {
    return auctionItemService.findAuctionItemById(id);
  }

  private void upload(
      List<MultipartFile> images, String from, AuctionItem auctionItem, Comment comment)
      throws IOException {
    for (MultipartFile file : images) {
      String originalFilename = getOriginalFilename(file);
      String fileExtension = getFileExtension(originalFilename);
      validateFileExtension(fileExtension);

      String fileName = uploadFileName(from, fileExtension);
      awsS3Service.upload(file, fileName);

      String fileShow = "https://d231cnlxdxmjew.cloudfront.net/" + fileName;
      saveImage(from, originalFilename, fileShow, auctionItem);
    }
  }

  private String getOriginalFilename(MultipartFile file) {
    String originalFilename = file.getOriginalFilename();
    if (originalFilename == null) {
      throw new ImageTypeException("파일 확장자 없음");
    }
    return originalFilename;
  }

  private String getFileExtension(String originalFilename) {
    return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
  }

  private void validateFileExtension(String filename) {
    String extension = getFileExtension(filename);
    if (!FileType.isValid(extension)) {
      throw new ImageTypeException("이미지 파일이 아님");
    }
  }

  private String uploadFileName(String from, String fileExtension) {
    return from + "/" + (UUID.randomUUID().toString().replace("-", "") + "." + fileExtension);
  }

  private void saveImage(
      String from, String originalFilename, String fileShowUrl, AuctionItem auctionItem) {
    Image image = Image.builder().imageFrom(from).name(originalFilename).url(fileShowUrl).build();
    image.setAuctionItem(auctionItem);
    imageRepository.save(image);
  }
}
