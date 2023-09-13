package megabrain.gyeongnamgyeongmae.global.infra.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationServiceInterface {

  @Value("${spring.mail.username}")
  private String username;

  final JavaMailSender mailSender;

  public void sendAuthenticationMail(String to, String subject, String content) {
    SimpleMailMessage mail = new SimpleMailMessage();
    mail.setTo(to);
    mail.setFrom(username);
    mail.setSubject(subject);
    mail.setText(content);
    mailSender.send(mail);
  }
}
