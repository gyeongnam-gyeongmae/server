package megabrain.gyeongnamgyeongmae.member.domain.entity;

import javax.persistence.*;
import lombok.*;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "pasword", nullable = false)
  private String password;

  @Column(name = "nickname", unique = true)
  private String nickname;

  @Column(name = "introduce")
  private String introduce;

  @Embedded private Address address;

  @Builder
  public Member(String email, String password, String nickname) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
  }
}
