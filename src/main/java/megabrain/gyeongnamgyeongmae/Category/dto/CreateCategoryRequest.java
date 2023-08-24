package megabrain.gyeongnamgyeongmae.Category.dto;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.Category.domain.entity.Category;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCategoryRequest {

  @NotNull private String name; // 카테고리 이름

  @Builder
  public CreateCategoryRequest(String name) {
    this.name = name;
  }

  public Category toEntity() {
    return Category.builder().name(this.name).build();
  }
}
