package megabrain.gyeongnamgyeongmae.member.domain.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
  private String state;
  private String city;
  private String town;
}
