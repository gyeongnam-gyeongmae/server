package megabrain.gyeongnamgyeongmae.member.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "members")
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

  @Embedded
  private Address address;
}
