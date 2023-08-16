package megabrain.gyeongnamgyeongmae.Category.dto;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCategoryRequest {

  @NotNull private String name; // 카테고리 이름

  @Builder
  public CreateCategoryRequest(String name) {
    this.name = name;
  }
}
