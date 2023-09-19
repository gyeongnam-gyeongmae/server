package megabrain.gyeongnamgyeongmae.domain.user.domain.repository;

import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Long getIdByAuthVendorUserId(String authVendorUserId);

  boolean existsByAuthVendorUserId(String authVendorUserId);
}
