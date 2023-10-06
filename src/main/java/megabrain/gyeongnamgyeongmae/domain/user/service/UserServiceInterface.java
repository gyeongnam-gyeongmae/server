package megabrain.gyeongnamgyeongmae.domain.user.service;

import java.util.List;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.Address;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.dto.UserUpdateRequest;

public interface UserServiceInterface {

  void registerUser(User user);

  User findUserById(Long Id);

  Long getIdByAuthVendorUserId(String authVendorUserId);

  void setAddress(Long userId, Address address);

  void withdrawUserById(Long id);

  void updateUser(User user, UserUpdateRequest userUpdateRequest);

  List<User> findAllUser();

  Address getAddressByCoordinate(Float latitude, Float longitude);
}
