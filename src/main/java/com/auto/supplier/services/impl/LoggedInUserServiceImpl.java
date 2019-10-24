package com.auto.supplier.services.impl;

import com.auto.supplier.entities.UserEntity;
import com.auto.supplier.services.LoggedInUserService;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LoggedInUserServiceImpl implements LoggedInUserService {

  @Override
  public Optional<UserEntity> getUser() {
    if (!isAuthenticated()) {
      return Optional.empty();
    }
    return Optional.ofNullable((UserEntity) getAuthentication().getPrincipal());
  }
}
