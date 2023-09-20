package megabrain.gyeongnamgyeongmae.domain.user.domain.entity;

import javax.persistence.*;
import lombok.*;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.global.BaseTimeEntity;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(name = "auth_vendor_user_id", unique = true)
  private String authVendorUserId;

  @Column(name = "phone_number", unique = true, length = 11)
  private String phoneNumber;

  @Column(name = "nickname", unique = true)
  private String nickname;

  @Column(name = "introduce")
  private String introduce;

  @Embedded private Address address;

  @Builder
  public User(String phoneNumber, String authVendorUserId, String nickname) {
    this.phoneNumber = phoneNumber;
    this.authVendorUserId = authVendorUserId;
    this.nickname = nickname;
  }
}