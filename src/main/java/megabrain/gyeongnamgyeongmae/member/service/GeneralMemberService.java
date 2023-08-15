package megabrain.gyeongnamgyeongmae.member.service;

import lombok.RequiredArgsConstructor;
import megabrain.gyeongnamgyeongmae.member.domain.entity.Member;
import megabrain.gyeongnamgyeongmae.member.domain.repository.MemberRepository;
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
  public boolean isDuplicatedEmail(String email) {
    return memberRepository.existsByEmail(email);
  }
}