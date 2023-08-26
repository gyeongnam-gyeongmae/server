package megabrain.gyeongnamgyeongmae.auctionItem.controller;

import static megabrain.gyeongnamgyeongmae.fixture.AuctionItemFixture.*;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import megabrain.gyeongnamgyeongmae.Category.service.CategoryService;
import megabrain.gyeongnamgyeongmae.auctionItem.domain.entity.AuctionItem;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.AuctionItemResponse;
import megabrain.gyeongnamgyeongmae.auctionItem.dto.UpDateAuctionItemRequest;
import megabrain.gyeongnamgyeongmae.auctionItem.service.Item.AuctionItemService;
import megabrain.gyeongnamgyeongmae.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(AuctionItemController.class)
public class AuctionControllerTest {

  @MockBean private CategoryService categoryService;
  @MockBean private AuctionItemService auctionItemService;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private MemberService memberService;

  @Autowired private MockMvc mockMvc;

  @BeforeEach
  void setUp(
      WebApplicationContext applicationContext, RestDocumentationContextProvider contextProvider) {

    mockMvc =
        MockMvcBuilders.webAppContextSetup(applicationContext)
            .apply(documentationConfiguration(contextProvider))
            .build();
  }

  private String toJsonString(Object object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }

  @Test
  @DisplayName("경매 상품 생성 테스트 만약 성공하면 201반환 ")
  void createAuctionItemTest() throws Exception {

    when(categoryService.findCategoryByName("phone")).thenReturn(NEW_CATEGORY);
    when(memberService.findMemberById(1L)).thenReturn(member);

    mockMvc
        .perform(
            post("/api/auctions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(CREATE_AUCTION_ITEM)))
        .andDo(print())
        .andExpect(status().isCreated());
  }

  @Test
  @DisplayName("경매상품 조회 성공 케이스")
  void getAuctionItem() throws Exception {

    when(auctionItemService.findAuctionItemById(1L)).thenReturn(AUCTION_ITEM);

    AuctionItemResponse expectedResponse = AuctionItemResponse.of(AUCTION_ITEM);

    mockMvc
        .perform(get("/api/auctions/1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(expectedResponse.getId()))
        .andExpect(jsonPath("$.name").value(expectedResponse.getName()))
        .andExpect(jsonPath("$.nickname").value(expectedResponse.getNickname()))
        .andExpect(jsonPath("$.price").value(expectedResponse.getPrice()))
        .andExpect(jsonPath("$.itemStatus").value(expectedResponse.getItemStatus().toString()))
        .andExpect(
            jsonPath("$.auctionStatus").value(expectedResponse.getAuctionStatus().toString()))
        .andExpect(jsonPath("$.createdTime", startsWith("2023-12-31T12:00")))
        .andExpect(jsonPath("$.closeTime", startsWith("2098-12-31T12:00")))
        .andExpect(jsonPath("$.modifiedTime", startsWith("2023-12-31T12:00")))
        .andExpect(jsonPath("$.address").value(expectedResponse.getAddress()))
        .andExpect(jsonPath("$.category").value(expectedResponse.getCategory()))
        .andExpect(jsonPath("$.email").value(expectedResponse.getEmail()))
        .andExpect(jsonPath("$.content").value(expectedResponse.getContent()))
        .andExpect(jsonPath("$.likeCount").value(expectedResponse.getLikeCount()))
        .andExpect(jsonPath("$.viewCount").value(expectedResponse.getViewCount()));
  }

  @Test
  @DisplayName("경매상품 수정 성공 케이스")
  void upDateAuctionItem() throws Exception {

    Long auctionItemId = 1L;
    when(auctionItemService.findAuctionItemById(auctionItemId)).thenReturn(AUCTION_ITEM);
    when(categoryService.findCategoryByName("mouse")).thenReturn(UPDATE_CATEGORY);

    UpDateAuctionItemRequest upDateAuctionItemRequest = UPDATE_AUCTION_ITEM;

    mockMvc
        .perform(
            put("/api/auctions/{auctionItemId}", auctionItemId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(upDateAuctionItemRequest)))
        .andDo(print())
        .andExpect(status().isOk());

    ArgumentCaptor<AuctionItem> captor = forClass(AuctionItem.class);

    verify(auctionItemService, times(1)).updateAuctionItem(captor.capture());

    AuctionItem capturedAuctionItem = captor.getValue();

    assertEquals("마우스 팝니다", capturedAuctionItem.getName());
    assertEquals("mouse", capturedAuctionItem.getCategory().getName());
    assertEquals(1000, capturedAuctionItem.getPrice());
    assertEquals("update content", capturedAuctionItem.getContent().getContent());
    assertEquals("USED", capturedAuctionItem.getContent().getStatus().toString());
    assertEquals("2066-12-31T12:00", capturedAuctionItem.getClosedTime().toString());
    assertEquals("CLOSED", capturedAuctionItem.getStatus().toString());
  }
}
