package megabrain.gyeongnamgyeongmae.domain.image.domain.repository;

import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.image.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
  // TODO: 중복제거 쿼리 제거
  @Query(value = "SELECT DISTINCT * FROM Image where auction_id = :id", nativeQuery = true)
  List<Image> findImageByAuctionItemId(Long id);

  @Query(
      value = "SELECT DISTINCT * FROM Image where auction_id = :id ORDER BY created_at ASC LIMIT 1",
      nativeQuery = true)
  Image findFirstImageByAuctionItemId(Long id);
}
