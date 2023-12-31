package megabrain.gyeongnamgyeongmae.domain.category.dto;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.category.domain.entity.Category;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCategoryRequest {

  @NotNull private String name;

  @Builder
  public CreateCategoryRequest(String name) {
    this.name = name;
  }

  public Category toEntity() {
    return Category.builder().name(this.name).build();
  }
}
