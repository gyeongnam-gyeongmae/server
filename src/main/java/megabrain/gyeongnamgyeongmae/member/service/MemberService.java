package megabrain.gyeongnamgyeongmae.member.service;

import megabrain.gyeongnamgyeongmae.member.domain.entity.Member;

public interface MemberService {

  public boolean isDuplicatedEmail(String email);

  public void createMember(Member member);
}
