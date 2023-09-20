package megabrain.gyeongnamgyeongmae.domain.user.domain.repository;

import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = "SELECT u.id FROM User u WHERE u.authVendorUserId=?1")
  Long getIdByAuthVendorUserId(String authVendorUserId);

  boolean existsByAuthVendorUserId(String authVendorUserId);
}
