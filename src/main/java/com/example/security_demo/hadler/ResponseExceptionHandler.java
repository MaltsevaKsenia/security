package com.example.security_demo.hadler;

import javax.validation.ConstraintViolationException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler {

  @ExceptionHandler(Exception.class)
  public String handleAll(Exception exception) {
    return "error";
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ModelMap handle(ConstraintViolationException exception) {
    return new ModelMap().addAttribute("error", exception.getMessage());
  }

}
