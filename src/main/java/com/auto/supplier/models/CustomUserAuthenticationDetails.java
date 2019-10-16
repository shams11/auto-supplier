package com.auto.supplier.models;

import com.auto.supplier.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserAuthenticationDetails extends UserEntity implements UserDetails {

  public CustomUserAuthenticationDetails(UserEntity userEntity) {
    super(userEntity);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> result = new ArrayList<>();
    getRoles().
        forEach(role ->
            role.getPermissions().forEach(permission ->
                result.add(new SimpleGrantedAuthority(permission.getUniqueName()))));
    return result;
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
