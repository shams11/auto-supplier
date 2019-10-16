/*
    Copyright (c) 2019 Afreen, Inc
    All rights reserved. Patents pending.
*/
package com.auto.supplier.security.authentication;

import com.auto.supplier.commons.services.authentication.UserAuthorityService;
import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@LoggingProfiler
public class UserEntityAuthorityService implements UserAuthorityService {

  @Override
  public List<GrantedAuthority> getAuthorities(Object object) {
    List<GrantedAuthority> result = new ArrayList<>();

    if (notUserEntityInstance(object)) {
      return result;
    }

    UserEntity user = (UserEntity) object;
    user.getRoles().forEach(role ->
        role.getPermissions().forEach(permission ->
            result.add(new SimpleGrantedAuthority(permission.getUniqueName()))));

    return result;
  }

  private boolean notUserEntityInstance(Object object) {
    return !(object instanceof UserEntity);
  }
}
