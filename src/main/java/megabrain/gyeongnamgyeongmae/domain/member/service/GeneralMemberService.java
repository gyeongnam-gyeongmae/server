package megabrain.gyeongnamgyeongmae.domain.member.service;

import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.domain.member.domain.entity.Member;
import megabrain.gyeongnamgyeongmae.domain.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GeneralMemberService implements MemberService {

  private final MemberRepository memberRepository;

  @Override
  @Transactional
  public void createMember(Member member) {
    memberRepository.save(member);
  }

  @Override
  public Member findMemberById(Long Id) {
    return memberRepository.findById(Id).orElseThrow(RuntimeException::new);
  }
}
