package com.auto.supplier.services;

import com.auto.supplier.entities.RoleEntity;
import java.util.Optional;

public interface RoleService {
  Optional<RoleEntity> findByUniqueName(String name);
}
