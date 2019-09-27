package com.example.security_demo.controller;

import com.example.security_demo.model.User;
import com.example.security_demo.service.UserServiceImpl;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {

  private final UserServiceImpl userService;

  @GetMapping("/")
  public String hello() {
    return "home";
  }

  @GetMapping("/home")
  public String home() {
    return "home";
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/hello")
  public String hello(Principal principal) {
    return "hello";
  }

  @GetMapping("/register")
  public String register(Principal principal) {
    return "registration";
  }

  @PostMapping("/register")
  public String register(@RequestParam("email") String email,
      @RequestParam("password") String password,
      @RequestParam("firstName") String firstName,
      @RequestParam("lastName") String lastName) {
    User user = User.builder()
        .enable(true)
        .email(email)
        .firstName(firstName)
        .lastName(lastName)
        .password(password).build();
    userService.saveUser(user);
    return "redirect:/login";
  }
}
