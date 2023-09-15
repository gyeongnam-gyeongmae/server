
//package megabrain.gyeongnamgyeongmae.member;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import megabrain.gyeongnamgyeongmae.member.domain.entity.Member;
//import megabrain.gyeongnamgyeongmae.member.domain.repository.MemberRepository;
//import megabrain.gyeongnamgyeongmae.member.dto.MemberCreateRequest;
//import megabrain.gyeongnamgyeongmae.member.service.GeneralMemberService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@ExtendWith(MockitoExtension.class)
//public class MemberServiceTest {
// package megabrain.gyeongnamgyeongmae.member;
//
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;
//
// import megabrain.gyeongnamgyeongmae.member.domain.entity.Member;
// import megabrain.gyeongnamgyeongmae.member.domain.repository.MemberRepository;
// import megabrain.gyeongnamgyeongmae.member.dto.MemberCreateRequest;
// import megabrain.gyeongnamgyeongmae.member.service.GeneralMemberService;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.security.crypto.password.PasswordEncoder;
//
// @ExtendWith(MockitoExtension.class)
// public class MemberServiceTest {
//
//  @InjectMocks private GeneralMemberService memberService;
//
//  @Mock private MemberRepository memberRepository;
//
//  @Mock private PasswordEncoder passwordEncoder;
//
//  private Member member;
//
//  private MemberCreateRequest memberCreateRequest;
//
//  @BeforeEach
//  void setup() {
//    when(passwordEncoder.encode(any())).thenReturn("testPassword");
//    memberCreateRequest =
//        MemberCreateRequest.builder()
//            .email("gg@admin.com")
//            .password("testPassword")
//            .nickname("김경남")
//            .build();
//
//    member = MemberCreateRequest.toEntity(memberCreateRequest, passwordEncoder);
//  }
//
//  @Test
//  @DisplayName("중복된 이메일이 존재하지 않는 경우 false를 반환한다.")
//  void isNotDuplicatedEmailExist() {
//    // given
//    when(memberRepository.existsByEmail(any())).thenReturn(false);
//
//    // then
//    assertFalse(memberService.isDuplicatedEmail(member.getEmail()));
//  }
//
//  @Test
//  @DisplayName("중복된 이메일이 존재하는 경우 true를 반환한다.")
//  void isDuplicatedEmailExist() {
//    // given
//    when(memberRepository.existsByEmail(any())).thenReturn(true);
//
//    // then
//    assertTrue(memberService.isDuplicatedEmail(member.getEmail()));
//  }
//}

