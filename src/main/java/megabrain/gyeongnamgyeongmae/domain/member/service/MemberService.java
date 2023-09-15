package megabrain.gyeongnamgyeongmae.domain.member.service;

import megabrain.gyeongnamgyeongmae.domain.member.domain.entity.Member;

public interface MemberService {

  void createMember(Member member);

  Member findMemberById(Long Id);
}
