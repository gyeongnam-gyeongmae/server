package megabrain.gyeongnamgyeongmae.Category.service;

import megabrain.gyeongnamgyeongmae.Category.domain.entity.Category;
import megabrain.gyeongnamgyeongmae.Category.dto.CreateCategoryRequest;

public interface ICategoryService {

  public Category findCategoryByName(String categoryName);

  public void createCategory(CreateCategoryRequest createCategoryRequest);
}
