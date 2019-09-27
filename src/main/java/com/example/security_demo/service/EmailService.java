package com.example.security_demo.service;

import java.nio.charset.StandardCharsets;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String mailFrom;

  @Value("${app.token.password.reset.duration}")
  private Long expiration;

  @SneakyThrows
  @Async
  public void sendEmailVerification(String emailVerificationUrl, String userEmailAddress) {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message,
        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
        StandardCharsets.UTF_8.name());

    helper.setTo(userEmailAddress);
    helper.setText("Hello", true);
    helper.setSubject("Verification email");
    helper.setFrom(mailFrom);
    mailSender.send(message);
  }
}
