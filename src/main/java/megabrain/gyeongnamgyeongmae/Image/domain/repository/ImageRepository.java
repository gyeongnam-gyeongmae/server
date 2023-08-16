package megabrain.gyeongnamgyeongmae.Image.domain.repository;

import megabrain.gyeongnamgyeongmae.Image.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {}
