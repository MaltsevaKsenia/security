package com.example.security_demo.controller;

import com.example.security_demo.model.User;
import com.example.security_demo.service.EmailService;
import com.example.security_demo.service.RegistrationCompleteService;
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

  private final EmailService emailService;

  private final RegistrationCompleteService registrationCompleteService;

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
        .enable(false)
        .email(email)
        .firstName(firstName)
        .lastName(lastName)
        .password(password).build();
    emailService.sendEmail(email);
    userService.saveUser(user);
    return "redirect:/home";
  }

  @GetMapping("/confirm")
  public String confirmRegistration(@RequestParam("token") String token) {
    registrationCompleteService.confirmEmailRegistration(token);
    return "redirect:/home";
  }
}
