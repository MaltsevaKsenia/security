package com.example.security_demo.exception;

public class TokenWasExpiredException extends RuntimeException {

  public TokenWasExpiredException(String message) {
    super(message);
  }
}
