package com.auto.supplier.services;

import com.auto.supplier.models.User;
import com.auto.supplier.commons.utils.SensitiveString;
import java.util.Optional;

public interface LoginService {
  Optional<User> login(String username, SensitiveString password);
}
