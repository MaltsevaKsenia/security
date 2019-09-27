package com.example.security_demo.service;

import com.example.security_demo.model.User;
import com.example.security_demo.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordChangingService {

  private final UserService userService;

  private final JwtTokenUtil jwtTokenUtil;

  private final PasswordEncoder passwordEncoder;

  public boolean verifyToken(String emailToken) {
    String userEmail = jwtTokenUtil.gwtCustomerEmail(emailToken);
    return jwtTokenUtil.isTokenNotExpired(emailToken);

  }

  public void changePassword(String oldPassword, String newPassword, String email) {
    User user = userService.loadUserByEmail(email);
    if(passwordEncoder.matches(user.getPassword(), passwordEncoder.encode(oldPassword))){
      userService.updatePassword(email, newPassword);
    }
    else throw new RuntimeException("password is wrong");
  }
}
