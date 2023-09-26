package megabrain.gyeongnamgyeongmae.domain.user.service;

import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;

public interface UserService {

  void registerUser(User user);

  User findUserById(Long Id);

  Long getIdByAuthVendorUserId(String authVendorUserId);

  void setAddress(Long userId, Float latitude, Float longitude);
}
