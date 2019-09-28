package com.example.security_demo.service.impl;

import com.example.security_demo.service.EmailService;
import com.example.security_demo.util.JwtTokenUtil;
import java.nio.charset.StandardCharsets;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;

  private final JwtTokenUtil jwtTokenUtil;

  @Value("${spring.mail.username}")
  private String mailFrom;

  @Value("${app.token.password.reset.duration}")
  private Long expiration;

  @Override
  @Async
  public void sendEmailForEmailConfirmation(String email) {
    String token = jwtTokenUtil.generateToken(email);

    String uri = UriComponentsBuilder
        .fromUriString("http://localhost:8080/confirm")
        .queryParam("token", token)
        .build().toUriString();

    String text = "Follow link for confirm email " + uri;
    sendEmail(email, text);
  }

  @Override
  @Async
  public void sendEmailForPasswordReset(String email) {
    String token = jwtTokenUtil.generateToken(email);

    String uri = UriComponentsBuilder
        .fromUriString("http://localhost:8080/change_password")
        .queryParam("token", token)
        .build().toUriString();

    String text = "Follow link for reset password " + uri;
    sendEmail(email, text);
  }

  @SneakyThrows
  private void sendEmail(String userEmailAddress, String text) {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message,
        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
        StandardCharsets.UTF_8.name());

    helper.setTo(userEmailAddress);
    helper.setText(text, true);
    helper.setSubject("Verification email");
    helper.setFrom(mailFrom);
    mailSender.send(message);
  }
}
