package megabrain.gyeongnamgyeongmae.domain.image.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.domain.auctionItem.service.Item.AuctionItemServiceInterface;
import megabrain.gyeongnamgyeongmae.domain.image.domain.entity.Image;
import megabrain.gyeongnamgyeongmae.domain.image.domain.repository.ImageRepository;
import megabrain.gyeongnamgyeongmae.domain.image.dto.FileType;
import megabrain.gyeongnamgyeongmae.domain.image.dto.UploadType;
import megabrain.gyeongnamgyeongmae.domain.image.exception.ImageTypeException;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.service.UserServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService implements ImageServiceInterface {

  private final ImageRepository imageRepository;
  private final AwsS3Service awsS3Service;
  private final AuctionItemServiceInterface auctionItemService;
  private final UserServiceInterface userService;

  @Override
  @Transactional
  public void uploadImage(List<MultipartFile> images, UploadType from, Long id) throws IOException {

    switch (from) {
      case AuctionItem:
        AuctionItemUpload(images, from.name(), checkIsRealIdAuctionItem(id));
        break;
      case Profile:
        profileUpload(images, from.name(), checkIsRealIdUser(id));
        break;
      default:
        throw new ImageTypeException("업로드 타입이 잘못됬습니다 : " + from.name());
    }
  }

  private User checkIsRealIdUser(Long id) {
    return userService.findUserById(id);
  }

  private AuctionItem checkIsRealIdAuctionItem(Long id) {
    return auctionItemService.findAuctionItemById(id);
  }

  private void AuctionItemUpload(List<MultipartFile> images, String from, AuctionItem auctionItem)
      throws IOException {
    for (MultipartFile file : images) {
      processUpload(from, file, image -> saveImage(image, auctionItem));
    }
  }

  private void profileUpload(List<MultipartFile> images, String from, User user)
      throws IOException {
    for (MultipartFile file : images) {
      processUpload(from, file, image -> saveImage(image, user));
    }
  }

  private void processUpload(String from, MultipartFile file, Consumer<Image> imageSaver)
      throws IOException {
    String originalFilename = getOriginalFilename(file);
    String fileExtension = getFileExtension(originalFilename);
    validateFileExtension(fileExtension);

    String fileName = uploadFileName(from, fileExtension);
    awsS3Service.upload(file, fileName);

    String fileShow = "https://d231cnlxdxmjew.cloudfront.net/" + fileName;
    Image image = Image.builder().imageFrom(from).name(originalFilename).url(fileShow).build();
    imageSaver.accept(image);
  }

  private void saveImage(Image image, AuctionItem auctionItem) {
    List<Image> images = findImageByAuctionItemId(auctionItem.getId());
    if (images != null) {
      images.forEach(Image::setRemoved);
    }
    image.setAuctionItem(auctionItem);
    imageRepository.save(image);
  }

  private List<Image> findImageByAuctionItemId(Long id) {
    return imageRepository.findImageByAuctionItemId(id);
  }

  private void saveImage(Image image, User user) {
    List<Image> images = user.getImages();
    if (images != null) {
      images.forEach(Image::setRemoved);
    }
    image.setUser(user);
    imageRepository.save(image);
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
}
