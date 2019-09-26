package com.example.security_demo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {

  private Integer userId;

  private String email;

  private String password;

  private String firstName;

  private String lastName;

  private Role role;

  private String restToken;
}
