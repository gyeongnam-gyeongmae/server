package megabrain.gyeongnamgyeongmae.global.infra.email;

public interface AuthenticationServiceInterface {
  void sendAuthenticationMail(String to, String subject, String content);
}
