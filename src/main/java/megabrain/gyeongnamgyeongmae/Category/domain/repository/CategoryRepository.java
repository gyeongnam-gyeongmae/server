package megabrain.gyeongnamgyeongmae.Category.domain.repository;

import java.util.Optional;
import megabrain.gyeongnamgyeongmae.Category.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  public Optional<Category> findCategoryByName(String Name);
}
