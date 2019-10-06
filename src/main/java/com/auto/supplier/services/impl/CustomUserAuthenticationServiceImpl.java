package com.auto.supplier.services.impl;

import com.auto.supplier.commons.exceptions.ServiceException;
import com.auto.supplier.commons.models.MessageKey;
import com.auto.supplier.models.CustomUserAuthenticationDetails;
import com.auto.supplier.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserAuthenticationServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Autowired
  public CustomUserAuthenticationServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public CustomUserAuthenticationDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .map(CustomUserAuthenticationDetails::new)
        .orElseThrow(() ->
            new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
                .args(username)
                .detailMessage(String.format("User not found for user name %s ", username))
                .build());
  }
}
