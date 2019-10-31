package com.auto.supplier.services;

import com.auto.supplier.entities.UserEntity;
import com.auto.supplier.models.User;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

public interface UserService {

  UserEntity create(User user, HttpServletRequest httpServletRequest) throws MalformedURLException;

  UserEntity getUserByUsername(String username);

  UserEntity getUserByEmail(String email);

  List<UserEntity> getAllUsers();

  void delete(UUID id);

  UserEntity update(UUID id, User user);

  void resetPassword(String resetToken, String newPassword);

  boolean isResetTokenValid(String token);
}
