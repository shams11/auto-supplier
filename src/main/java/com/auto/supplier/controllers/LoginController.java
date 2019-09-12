package com.auto.supplier.controllers;

import com.auto.supplier.models.User;
import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.commons.utils.SensitiveString;
import com.auto.supplier.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@LoggingProfiler
@RestController
@RequestMapping("/v1/login")
public class LoginController {
  private final LoginService loginService;

  @Autowired
  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public User login(@RequestParam(name = "username") String username,
                    @RequestParam("password") SensitiveString password) {
    return loginService.login(username, password).get(); // TODO : Need to replace get()
  }
}
