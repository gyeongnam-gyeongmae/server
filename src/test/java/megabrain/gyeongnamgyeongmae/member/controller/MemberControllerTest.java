// package megabrain.gyeongnamgyeongmae.member.controller;
//
// import static fixture.MemberFixture.MEMBER_REGISTRATION_REQUEST;
// import static fixture.MemberFixture.NEW_MEMBER;
// import static org.mockito.Mockito.doNothing;
// import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
// import static
// org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
// import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
// import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
// import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import megabrain.gyeongnamgyeongmae.member.service.MemberService;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.restdocs.RestDocumentationContextProvider;
// import org.springframework.restdocs.RestDocumentationExtension;
// import org.springframework.restdocs.payload.JsonFieldType;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.web.context.WebApplicationContext;
//
// @ExtendWith(RestDocumentationExtension.class)
// @WebMvcTest(MemberController.class)
// public class MemberControllerTest {
//  @MockBean private MemberService memberService;
//
//  @MockBean private PasswordEncoder passwordEncoder;
//
//  private MockMvc mockMvc;
//
//  @Autowired private ObjectMapper objectMapper;
//
//  @BeforeEach
//  void setUp(
//      WebApplicationContext applicationContext, RestDocumentationContextProvider contextProvider)
// {
//
//    mockMvc =
//        MockMvcBuilders.webAppContextSetup(applicationContext)
//            .apply(documentationConfiguration(contextProvider))
//            .build();
//  }
//
//  private String toJsonString(Object object) throws JsonProcessingException {
//    return objectMapper.writeValueAsString(object);
//  }
//
//  @Test
//  @DisplayName("회원가입에 성공할 경우 HTTP 상태코드 201을 반환한다.")
//  void successToRegistrationMember() throws Exception {
//
//    doNothing().when(memberService).createMember(NEW_MEMBER);
//    mockMvc
//        .perform(
//            post("/api/members/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(toJsonString(MEMBER_REGISTRATION_REQUEST)))
//        .andDo(print())
//        .andExpect(status().isCreated())
//        .andDo(
//            document(
//                "members/create/success",
//                preprocessRequest(prettyPrint()),
//                preprocessResponse(prettyPrint()),
//                requestFields(
//                    fieldWithPath("email")
//                        .type(JsonFieldType.STRING)
//                        .description("로그인시 사용할 사용자 이메일"),
//                    fieldWithPath("password")
//                        .type(JsonFieldType.STRING)
//                        .description("하나 이상의 대소문자, 숫자, 특수문자를 포함한 8자 이상 16자 이하의 비밀번호"),
//                    fieldWithPath("nickname").type(JsonFieldType.STRING).description("사용자의
// 닉네임"))));
//  }
// }
