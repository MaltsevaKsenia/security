package com.example.security_demo.controller;

import com.example.security_demo.exception.InternalValidationException;
import com.example.security_demo.service.EmailService;
import com.example.security_demo.service.UserService;
import com.example.security_demo.util.JwtTokenUtil;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Validated
@Controller
@RequiredArgsConstructor
public class PasswordResetController {

  private final JwtTokenUtil jwtTokenUtil;

  private final EmailService emailService;

  private final UserService userService;

  @GetMapping("/change_password")
  public String changePassword(@RequestParam("token") String token,
      ModelMap modelMap1) {
    String email = jwtTokenUtil.verifyAndParseToken(token);
    modelMap1.addAttribute("email", email);
    return "password";
  }

  @GetMapping("/enter_email")
  public String sendEmail() {
    return "enter_email";
  }

  @PostMapping("/enter_email")
  public String changePasswordGet(@ModelAttribute("email") @Email @NotNull String email,
      ModelMap modelMap) {
    emailService.sendEmailForPasswordReset(email);
    modelMap.addAttribute("message", "Email for password reset was sent");
    return "login";
  }

  @PostMapping("/password")
  public String password(@RequestParam("password") String password,
      @RequestParam("confirmPassword") String confirmPassword,
      @RequestParam("email") String email) {
    if (password.equals(confirmPassword)) {
      userService.updatePassword(email, password);
    } else {
      throw new InternalValidationException("Passwords don't match");
    }
    return "login";
  }
}
