package com.example.security_demo.service;

import com.example.security_demo.model.User;
import com.example.security_demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public void saveUser(User user) {
    userRepository.saveUser(user);
  }
}
