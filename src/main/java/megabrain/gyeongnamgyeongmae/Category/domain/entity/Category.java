package megabrain.gyeongnamgyeongmae.Category.domain.entity;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private Long id; // 카테고리 id

  @Column(name = "category_name")
  private String name; // 카테고리 이름

  @Builder
  public Category(String name) {
    this.name = name;
  }
}
