package megabrain.gyeongnamgyeongmae.domain.category.controller;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.category.dto.CreateCategoryRequest;
import megabrain.gyeongnamgyeongmae.domain.category.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카테고리 만들기", description = "카테고리를 만드는 api")
@RequestMapping("api/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @PostMapping()
  @Operation(summary = "카테고리 생성", description = "카테고리를 생성합니다.")
  public ResponseEntity<HttpStatus> createCategory(
      @RequestBody @Valid CreateCategoryRequest createCategoryRequest) {
    categoryService.createCategory(createCategoryRequest.getName());
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
