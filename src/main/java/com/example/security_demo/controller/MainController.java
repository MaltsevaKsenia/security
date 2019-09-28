package com.example.security_demo.controller;

import com.example.security_demo.model.User;
import com.example.security_demo.service.EmailService;
import com.example.security_demo.service.UserService;
import com.example.security_demo.util.JwtTokenUtil;
import java.security.Principal;
import java.util.Map;
import javax.jws.WebParam.Mode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
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
