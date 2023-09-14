package megabrain.gyeongnamgyeongmae.domain.category.service;

import megabrain.gyeongnamgyeongmae.domain.category.domain.entity.Category;

public interface ICategoryService {

  Category findCategoryByName(String categoryName);

  void createCategory(String categoryName);
}
