package megabrain.gyeongnamgyeongmae.domain.user.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import megabrain.gyeongnamgyeongmae.domain.user.domain.repository.UserRepository;
import megabrain.gyeongnamgyeongmae.domain.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GeneralUserService implements UserServiceInterface {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public void registerUser(User user) {
    userRepository.save(user);
  }

  @Override
  public User findUserById(Long Id) {
    return userRepository.findById(Id).orElseThrow(UserNotFoundException::new);
  }

  @Override
  public Long getIdByAuthVendorUserId(String authVendorUserId) {
    return this.userRepository.getIdByAuthVendorUserId(authVendorUserId);
  }

  @Override
  public List<User> findAllUser() {
    return userRepository.findAll();
  }
}
