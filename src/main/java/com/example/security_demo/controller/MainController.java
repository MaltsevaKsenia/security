package com.example.security_demo.controller;

import com.example.security_demo.model.User;
import com.example.security_demo.service.EmailService;
import com.example.security_demo.service.UserService;
import com.example.security_demo.util.JwtTokenUtil;
import java.security.Principal;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Validated
public class MainController {

  private final UserService userService;

  private final EmailService emailService;

  private final JwtTokenUtil jwtTokenUtil;

  @GetMapping("/")
  public String index() {
    return "redirect:/account";
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/account")
  public String account(Principal principal, ModelMap modelMap) {
    modelMap.addAttribute("greeting", "Hello, " + principal.getName());
    return "account";
  }

  @GetMapping("/register")
  public String register() {
    return "register";
  }

  @PostMapping("/register")
  public String register(@RequestParam("email") @NotNull @Email String email,
      @RequestParam("password") @NotNull @Size(min = 6) String password,
      @RequestParam("firstName") String firstName,
      @RequestParam("lastName") String lastName) {
    User user = User.builder()
        .enable(false)
        .email(email)
        .firstName(firstName)
        .lastName(lastName)
        .password(password).build();
    //todo check if user not already exist
    //todo is token was expired resend it
    emailService.sendEmailForEmailConfirmation(email);
    userService.saveUser(user);
    return "redirect:/login";
  }

  @GetMapping("/confirm")
  public String confirmRegistration(@RequestParam("token") String token) {
    String email = jwtTokenUtil.verifyAndParseToken(token);
    userService.enableUser(email);
    return "redirect:/login";
  }
}
