package megabrain.gyeongnamgyeongmae.Category.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.Category.dto.CreateCategoryRequest;
import megabrain.gyeongnamgyeongmae.Category.service.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/category")
public class CategoryController {
  private final ICategoryService categoryService;

  @PostMapping()
  public ResponseEntity<HttpStatus> createCategory(
      @RequestBody @Valid CreateCategoryRequest createCategoryRequest) {
    categoryService.createCategory(createCategoryRequest);
    return null;
  }
}
