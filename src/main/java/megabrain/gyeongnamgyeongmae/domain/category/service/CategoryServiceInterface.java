package megabrain.gyeongnamgyeongmae.domain.category.service;

import megabrain.gyeongnamgyeongmae.domain.category.domain.entity.Category;

public interface CategoryServiceInterface {

  Category findCategoryByName(String categoryName);

  void createCategory(String categoryName);
}
