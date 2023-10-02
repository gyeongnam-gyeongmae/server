package megabrain.gyeongnamgyeongmae.domain.image.domain.repository;

import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.image.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

  @Query(
      value = "SELECT * FROM images where auction_id = :id AND removed = FALSE",
      nativeQuery = true)
  List<Image> findImageByAuctionItemId(Long id);

  @Query(
      value =
          "SELECT  * FROM images where auction_id = :id AND removed = FALSE ORDER BY created_at ASC LIMIT 1 ",
      nativeQuery = true)
  Image findFirstImageByAuctionItemId(Long id);
}
