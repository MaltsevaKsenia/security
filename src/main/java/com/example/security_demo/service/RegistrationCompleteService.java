package com.example.security_demo.service;

import com.example.security_demo.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationCompleteService {

  private final UserService userService;

  private final JwtTokenUtil jwtTokenUtil;

  public void confirmEmailRegistration(String emailToken) {
    String userEmail = jwtTokenUtil.gwtCustomerEmail(emailToken);
    Boolean notExpired = jwtTokenUtil.isTokenNotExpired(emailToken);
    if (userEmail != null && notExpired) {
      userService.enableUser(userEmail);
    } else {
      throw new RuntimeException("Cannot parse token");
    }

  }
}
