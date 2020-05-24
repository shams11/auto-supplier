package com.auto.supplier.controllers;

import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.commons.utils.SensitiveString;
import com.auto.supplier.mappers.UserMapper;
import com.auto.supplier.models.User;
import com.auto.supplier.services.UserService;
import com.auto.supplier.services.impl.CustomUserAuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@LoggingProfiler
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/login")
public class LoginController {

  private final UserService userService;
  private final CustomUserAuthenticationServiceImpl customUserAuthenticationService;
  private final UserMapper userMapper;

  @PostMapping(
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public User login(@RequestParam(name = "username") String username,
                                               @RequestParam("password") SensitiveString password) {

    return userMapper.toPojo(userService.getUserByUsername(username));
  }
}
