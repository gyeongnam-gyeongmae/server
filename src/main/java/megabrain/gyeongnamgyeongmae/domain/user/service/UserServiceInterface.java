package megabrain.gyeongnamgyeongmae.domain.user.service;

import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;

public interface UserServiceInterface {

  void registerUser(User user);

  User findUserById(Long Id);

  Long getIdByAuthVendorUserId(String authVendorUserId);

  List<User> findAllUser();
}
