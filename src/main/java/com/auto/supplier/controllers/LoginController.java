package com.auto.supplier.controllers;

import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.commons.utils.SensitiveString;
import com.auto.supplier.mappers.UserMapper;
import com.auto.supplier.models.User;
import com.auto.supplier.services.LoginService;
import com.auto.supplier.services.UserService;
import com.auto.supplier.services.impl.CustomUserAuthenticationServiceImpl;
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
  private final UserService userService;
  private final CustomUserAuthenticationServiceImpl customUserAuthenticationService;
  private final UserMapper userMapper;

  @Autowired
  public LoginController(UserService userService,
                         UserMapper userMapper,
                         CustomUserAuthenticationServiceImpl customUserAuthenticationService) {
    this.userService = userService;
    this.customUserAuthenticationService = customUserAuthenticationService;
    this.userMapper = userMapper;
  }


  @PostMapping(
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public User login(@RequestParam(name = "username") String username,
                                               @RequestParam("password") SensitiveString password) {

    return userMapper.toPojo(userService.getUserByUsername(username));
  }
}
