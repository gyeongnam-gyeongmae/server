package megabrain.gyeongnamgyeongmae.Category.service;

import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.Category.domain.entity.Category;
import megabrain.gyeongnamgyeongmae.Category.domain.repository.CategoryRepository;
import megabrain.gyeongnamgyeongmae.Category.dto.CreateCategoryRequest;
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
  public void createCategory(CreateCategoryRequest createCategoryRequest) {
    Category category = Category.builder().name(String.valueOf(createCategoryRequest)).build();
    categoryRepository.save(category);
  }
}
