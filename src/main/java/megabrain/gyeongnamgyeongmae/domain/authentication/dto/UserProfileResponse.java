package megabrain.gyeongnamgyeongmae.domain.authentication.dto;

import lombok.*;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.Address;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserProfileResponse {
  private Long id;
  private String phoneNumber;
  private String nickname;
  private String introduce;
  private Address address;

  private String profileImageUrl;

  public static UserProfileResponse of(User user) {
    return UserProfileResponse.builder()
        .id(user.getId())
        .phoneNumber(user.getPhoneNumber())
        .nickname(user.getNickname())
        .introduce(user.getIntroduce())
        .address(user.getAddress())
        .profileImageUrl("https://yt3.ggpht.com/a/default-user=s88-c-k-c0x00ffffff-no-rj")
        .build();
  }

  public void setNewImageUrl(String newImageUrl) {
    this.profileImageUrl = newImageUrl;
  }
}
