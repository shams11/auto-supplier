package com.auto.supplier.services.impl;

import com.auto.supplier.models.User;
import com.auto.supplier.commons.utils.SensitiveString;
import com.auto.supplier.services.LoginService;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

  @Override
  public User login(String username, SensitiveString password) {
    return null;
  }
}
