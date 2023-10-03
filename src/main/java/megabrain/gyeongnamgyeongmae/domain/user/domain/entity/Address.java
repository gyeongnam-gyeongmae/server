package megabrain.gyeongnamgyeongmae.domain.user.domain.entity;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
  private String state; // 시도
  private String city; // 시군구
  private String town; // 읍면동

  private Address(String state, String city, String town) {
    this.state = state;
    this.city = city;
    this.town = town;
  }

  public static Address of(String state, String city, String town) {
    return new Address(state, city, town);
  }
}
