package megabrain.gyeongnamgyeongmae.category.service;

import megabrain.gyeongnamgyeongmae.category.domain.entity.Category;

public interface ICategoryService {

  Category findCategoryByName(String categoryName);

  void createCategory(String categoryName);
}
