package com.example.security_demo.service;

public interface EmailService {

  void sendEmailForEmailConfirmation(String email);

  void sendEmailForPasswordReset(String email);
}
