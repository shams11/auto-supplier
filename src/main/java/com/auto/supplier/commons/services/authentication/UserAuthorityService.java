package com.auto.supplier.commons.services.authentication;

import org.springframework.security.core.GrantedAuthority;
import java.util.List;

public interface UserAuthorityService {
  List<GrantedAuthority> getAuthorities(Object var1);
}
