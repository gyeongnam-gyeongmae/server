package megabrain.gyeongnamgyeongmae.Category.service;

import megabrain.gyeongnamgyeongmae.Category.domain.entity.Category;

public interface ICategoryService {

  Category findCategoryByName(String categoryName);

  void createCategory(String categoryName);
}
