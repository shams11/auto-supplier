package com.auto.supplier.models;

import com.auto.supplier.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserAuthenticationDetails extends UserEntity implements UserDetails {

  public CustomUserAuthenticationDetails(UserEntity userEntity) {
    super(userEntity);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return getRoles()
        .stream()
        .map(roleEntity -> new SimpleGrantedAuthority("ROLE_" + roleEntity.getUniqueName()))
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return super.getPassword();
  }

  @Override
  public String getUsername() {
    return super.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public String getEmail() {
    return super.getEmail();
  }
}
