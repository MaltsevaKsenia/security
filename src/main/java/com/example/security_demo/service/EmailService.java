package com.example.security_demo.service;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender mailSender;

  private final JwtTokenUtil jwtTokenUtil;

  private final UserService userService;

  @Value("${spring.mail.username}")
  private String mailFrom;

  @Value("${app.token.password.reset.duration}")
  private Long expiration;

  @SneakyThrows
  @Async
  public void sendEmail(String userEmailAddress) {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message,
        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
        StandardCharsets.UTF_8.name());

    helper.setTo(userEmailAddress);
    helper.setText(
        "Hello, go there to verify email " + generateUrlForEmailVerification(userEmailAddress),
        true);
    helper.setSubject("Verification email");
    helper.setFrom(mailFrom);
    mailSender.send(message);
  }

  private String generateUrlForEmailVerification(String email) {
    String token = jwtTokenUtil.generateToken(email);
    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/confirm");
    return urlBuilder.queryParam("token", token).toUriString();
  }

  @SneakyThrows
  @Async
  public void sendPassword(String userEmailAddress) {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message,
        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
        StandardCharsets.UTF_8.name());

    helper.setTo(userEmailAddress);
    helper.setText(
        "Hello, go there change email " + generatEmailForPasswordChanhging(userEmailAddress),
        true);
    helper.setSubject("Verification email");
    helper.setFrom(mailFrom);
    mailSender.send(message);
  }

  private String generatEmailForPasswordChanhging(String email) {
    String token = jwtTokenUtil.generateToken(email);
    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/change_password");
    return urlBuilder.queryParam("token", token).toUriString();
  }

}
