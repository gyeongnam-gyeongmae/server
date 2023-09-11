package megabrain.gyeongnamgyeongmae.category.service;

import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.category.domain.entity.Category;
import megabrain.gyeongnamgyeongmae.category.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  public Category findCategoryByName(String categoryName) {
    return categoryRepository.findCategoryByName(categoryName).orElseThrow(RuntimeException::new);
  }

  @Override
  public void createCategory(String categoryName) {
    Category category = Category.builder().name(categoryName).build();
    categoryRepository.save(category);
  }
}
