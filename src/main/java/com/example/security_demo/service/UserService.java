package com.example.security_demo.service;

import com.example.security_demo.model.User;

public interface UserService {

  void saveUser(User user);

  User loadUserByEmail(String email);

  void enableUser(String user);
}
