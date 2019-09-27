package com.example.security_demo.service;

import com.example.security_demo.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  void saveUser(User user);

  User loadUserByEmail(String email);

  void enableUser(String user);

  UserDetails loadUserByUsername(String s);

  void updatePassword(String email, String newPassword);

}
