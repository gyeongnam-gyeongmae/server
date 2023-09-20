package megabrain.gyeongnamgyeongmae.domain.user.domain.repository;

import megabrain.gyeongnamgyeongmae.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = "SELECT u.id FROM User u WHERE u.authVendorUserId=?1")
=======

public interface UserRepository extends JpaRepository<User, Long> {

>>>>>>> 4a39bbb72f5763f76cf87b49cd016a02904318bf
  Long getIdByAuthVendorUserId(String authVendorUserId);

  boolean existsByAuthVendorUserId(String authVendorUserId);
}
