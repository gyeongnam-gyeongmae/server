package megabrain.gyeongnamgyeongmae.member.service;

import megabrain.gyeongnamgyeongmae.member.domain.entity.Member;

public interface MemberService {

  boolean isDuplicatedEmail(String email);

  void createMember(Member member);

  Member findMemberById(Long Id);
}
