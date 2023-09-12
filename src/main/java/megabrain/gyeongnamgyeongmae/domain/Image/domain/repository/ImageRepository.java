package megabrain.gyeongnamgyeongmae.domain.Image.domain.repository;

import megabrain.gyeongnamgyeongmae.domain.Image.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {}
