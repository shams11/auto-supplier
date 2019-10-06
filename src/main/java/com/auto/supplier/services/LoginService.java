package com.auto.supplier.services;

import com.auto.supplier.models.User;
import com.auto.supplier.commons.utils.SensitiveString;

public interface LoginService {
  User login(String username, SensitiveString password);
}
