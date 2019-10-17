package com.auto.supplier.services;

import com.auto.supplier.models.User;
import com.auto.supplier.entities.UserEntity;
import java.util.List;
import java.util.UUID;

public interface UserService {

  UserEntity create(User user);

  UserEntity getUserByUsername(String username);

  UserEntity getUserByEmail(String email);

  List<UserEntity> getAllUsers();

  void delete(UUID id);

  UserEntity update(UUID id, User user);
}
