package com.auto.supplier.controllers;

import com.auto.supplier.models.User;
import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.mappers.UserMapper;
import com.auto.supplier.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@LoggingProfiler
@RequestMapping("/v1/users")
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;

  @Autowired
  public UserController(UserService userService, UserMapper userMapper) {
    this.userService = userService;
    this.userMapper = userMapper;
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  @ResponseStatus(HttpStatus.CREATED)
  public User createUser(
      @RequestBody @Valid User user, HttpServletRequest httpServletRequest)
      throws MalformedURLException {
    return userMapper.toPojo(userService.create(user, httpServletRequest));
  }

  @PutMapping(value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

  public User update(
      @PathVariable("id") UUID id,
      @RequestBody @Valid User user) {

    return userMapper.toPojo(userService.update(id, user));
  }

  @GetMapping(params = "username",
      produces = MediaType.APPLICATION_JSON_VALUE)
  //@JsonView({View.UserDetails.class}) //TODO : Need to correct this
  public User getUsersByUsername(@RequestParam(value = "username") String username) {
    return userMapper.toPojo(userService.getUserByUsername(username));
  }

  @GetMapping(params = "email",
      produces = MediaType.APPLICATION_JSON_VALUE)
  //@JsonView({View.UserDetails.class}) //TODO : Need to correct this
  public User getUsersByEmail(@RequestParam(value = "email") String email) {
    return userMapper.toPojo(userService.getUserByEmail(email));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> getAllUsers() {
    return userService.getAllUsers().stream()
        .map(userMapper::toPojo)
        .collect(Collectors.toList());
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") UUID id) {
    userService.delete(id);
  }

  @PutMapping(value = "/reset-password",
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public void resetPassword(@RequestParam(value = "token", required = false) String token,
                            @RequestParam(value = "password") String newPassword) {

    userService.resetPassword(token, newPassword);
  }

  @GetMapping(value = "/is-token-valid")
  public boolean isResetTokenValid(@RequestParam("token") String token) {
    return userService.isResetTokenValid(token);
  }
}
