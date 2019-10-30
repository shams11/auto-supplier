package com.auto.supplier.controllers;

import com.auto.supplier.commons.utils.LoggingProfiler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@LoggingProfiler
@RestController
@RequestMapping("/v1/reset-password")
public class ResetPasswordController {

  // Display form to reset password
  @GetMapping
  public boolean isValidTolen(@RequestParam("token") String token) {

  return false;
  }

}
