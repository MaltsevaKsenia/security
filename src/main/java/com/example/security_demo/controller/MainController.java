package com.example.security_demo.controller;

import com.example.security_demo.model.User;
import com.example.security_demo.service.EmailService;
import com.example.security_demo.service.PasswordChangingService;
import com.example.security_demo.service.RegistrationCompleteService;
import com.example.security_demo.service.UserServiceImpl;
import com.example.security_demo.util.JwtTokenUtil;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MainController {

  private final UserServiceImpl userService;

  private final EmailService emailService;

  private final RegistrationCompleteService registrationCompleteService;

  private final PasswordChangingService passwordChangingService;

  private final JwtTokenUtil jwtTokenUtil;

  @GetMapping("/")
  public String index() {
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

  @GetMapping("/account")
  public String account(Principal principal) {
    return "account";
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
    return "redirect:/login";
  }

  @GetMapping("/change_password")
  public String changePassword(@RequestParam("token") String token, RedirectAttributes modelMap,
      ModelMap modelMap1) {
    if (passwordChangingService.verifyToken(token)) {
      String email = jwtTokenUtil.gwtCustomerEmail(token);
      modelMap.addAttribute("email", email);
      modelMap1.addAttribute("email", email);

      return "password";
    } else {
      return "home";
    }
  }

  @GetMapping("/enter_email")
  public String sendEmail(Principal principal) {
    return "enter_email";
  }

  @PostMapping("/enter_email")
  public String changePasswordGet(@ModelAttribute("email") String email, ModelMap modelMap) {
    emailService.sendPassword(email);
    modelMap.addAttribute("message", "Check you email");
    return "login";
  }

  @PostMapping("/password")
  public String password(@RequestParam("password") String password,
      @ModelAttribute("email") String email) {
    userService.updatePassword(email, password);
    return "login";
  }


}
