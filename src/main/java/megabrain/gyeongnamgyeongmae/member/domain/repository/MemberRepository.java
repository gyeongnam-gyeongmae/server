package megabrain.gyeongnamgyeongmae.member.domain.repository;

import megabrain.gyeongnamgyeongmae.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

  public boolean existsByEmail(String email);
}
