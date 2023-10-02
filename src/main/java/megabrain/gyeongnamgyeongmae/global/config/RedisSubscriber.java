package megabrain.gyeongnamgyeongmae.global.config;

// @Component
// @RequiredArgsConstructor
// public class RedisSubscriber implements MessageListener {
//  private final ObjectMapper objectMapper;
//  private final SimpMessageSendingOperations messagingTemplate;
//
//  @Override
//  public void onMessage(Message message, byte[] pattern) {
//    try {
//      String subscribeMessage = new String(message.getBody());
//      RecentChatMessageResponse response =
//          objectMapper.readValue(subscribeMessage, RecentChatMessageResponse.class);
//      messagingTemplate.convertAndSend(getSendTopic(response.getRoomId()), response);
//      // log.info("[INFO] MessageListener TOPIC ID : {} ", getSendTopic(response.getRoomId()));
//    } catch (JsonProcessingException e) {
//      // log.error("[Error] MessageListener ", e);
//    }
//  }
//
//  private String getSendTopic(Long id) {
//    return String.format("/subscribe/chat-rooms/%d", id);
//  }
// }
