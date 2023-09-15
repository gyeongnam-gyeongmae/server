package megabrain.gyeongnamgyeongmae.domain.member.domain.repository;

import megabrain.gyeongnamgyeongmae.domain.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

  boolean existsByEmail(String email);

  boolean existsByAuthVendorMemberId(String authVendorMemberId);
}
