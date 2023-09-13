package megabrain.gyeongnamgyeongmae.global.infra.email;

public interface AuthenticationServiceInterface {
  String generateMailAuthenticationCode();

  String generateCellularPhoneAuthenticationCode();

  void sendAuthenticationMail(String email, String subject, String content);

  void sendCellularPhoneAuthenticationCode(String phoneNumber, String phoneAuthenticationCode);
}
