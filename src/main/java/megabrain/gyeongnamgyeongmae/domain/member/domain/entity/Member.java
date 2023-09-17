package megabrain.gyeongnamgyeongmae.domain.member.domain.entity;

import javax.persistence.*;
import lombok.*;
import lombok.NoArgsConstructor;
import megabrain.gyeongnamgyeongmae.global.commons.BaseTimeEntity;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  @Column(name = "auth_vendor_memebr_id", unique = true)
  private String authVendorMemberId;

  @Column(name = "auth_vendor")
  private int authVendor;

  @Column(name = "phone_number", unique = true, length = 11)
  private String phoneNumber;

  @Column(name = "pasword", nullable = false)
  private String password;

  @Column(name = "nickname", unique = true)
  private String nickname;

  @Column(name = "introduce")
  private String introduce;

  @Embedded private Address address;

  @Builder
  public Member(
      String phoneNumber,
      String authVendorMemberId,
      int authVendor,
      String password,
      String nickname) {
    this.phoneNumber = phoneNumber;
    this.authVendorMemberId = authVendorMemberId;
    this.authVendor = authVendor;
    this.password = password;
    this.nickname = nickname;
  }
}
