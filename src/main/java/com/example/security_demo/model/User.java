package com.example.security_demo.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class User {

  private Integer userId;

  private String email;

  private String password;

  private String firstName;

  private String lastName;

  private Role role;

  private String restToken;

  private Boolean enable;

  public User(String email, String password, Role role) {
    this.email = email;
    this.password = password;
    this.role = role;
  }
}
