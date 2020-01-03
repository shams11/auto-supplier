package com.auto.supplier.services;

import com.auto.supplier.entities.OrgEntity;
import com.auto.supplier.entities.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.Optional;

public interface LoggedInUserService {

  default boolean isAuthenticated() {
    Authentication authentication = getAuthentication();
    return authentication != null;
  }

  default Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  Optional<UserEntity> getUser();

  Optional<OrgEntity> getOrg();
}
