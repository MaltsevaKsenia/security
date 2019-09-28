package com.example.security_demo.service.impl;

import com.example.security_demo.model.User;
import com.example.security_demo.repository.UserRepository;
import com.example.security_demo.service.UserService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = userRepository.loadUserByEmail(s);
    GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        Collections.singletonList(authority)
    );
  }

  @Override
  public void updatePassword(String email, String newPassword) {
    userRepository.updatePassword(email, newPassword);
  }
}
