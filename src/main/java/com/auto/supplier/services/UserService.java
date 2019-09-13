package com.auto.supplier.services;

import com.auto.supplier.models.User;
import com.auto.supplier.entities.UserEntity;
import java.util.List;
import java.util.UUID;

public interface UserService {

  UserEntity createUser(User user);

  UserEntity getUserByUsername(String username);

  List<UserEntity> getAllUsers();

  void deleteOrg(UUID id);
}
