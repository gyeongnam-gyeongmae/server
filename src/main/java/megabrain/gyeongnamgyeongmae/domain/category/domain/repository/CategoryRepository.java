package megabrain.gyeongnamgyeongmae.domain.category.domain.repository;

import java.util.Optional;
import megabrain.gyeongnamgyeongmae.domain.category.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  Optional<Category> findCategoryByName(String Name);
}
