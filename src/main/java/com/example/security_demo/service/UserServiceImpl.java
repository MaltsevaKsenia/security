package com.example.security_demo.service;

import com.example.security_demo.model.User;
import com.example.security_demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public void saveUser(User user) {
    userRepository.saveUser(user);
  }

  @Override
  public void enableUser(String user) {
    userRepository.enableUser(user);
  }

  @Override
  public User loadUserByEmail(String email) {
    return userRepository.loadUserByEmail(email);
  }
}
