package megabrain.gyeongnamgyeongmae.domain.category.service;

import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.category.domain.entity.Category;
import megabrain.gyeongnamgyeongmae.domain.category.domain.repository.CategoryRepository;
import megabrain.gyeongnamgyeongmae.domain.category.exception.CategoryNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryServiceInterface {

  private final CategoryRepository categoryRepository;

  @Override
  public Category findCategoryByName(String categoryName) {
    return categoryRepository.findCategoryByName(categoryName).orElseThrow(()-> new CategoryNotFoundException("카테고리를 찾을수 없습니다"));
  }

  @Override
  public void createCategory(String categoryName) {
    Category category = Category.builder().name(categoryName).build();
    categoryRepository.save(category);
  }
}
