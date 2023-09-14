package megabrain.gyeongnamgyeongmae.domain.category.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.category.dto.CreateCategoryRequest;
import megabrain.gyeongnamgyeongmae.domain.category.service.CategoryService;
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
  private final CategoryService categoryService;

  @PostMapping()
  public ResponseEntity<HttpStatus> createCategory(
      @RequestBody @Valid CreateCategoryRequest createCategoryRequest) {
    categoryService.createCategory(createCategoryRequest.getName());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
