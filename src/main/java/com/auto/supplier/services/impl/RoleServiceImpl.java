package com.auto.supplier.services.impl;

import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.entities.RoleEntity;
import com.auto.supplier.repositories.RoleRepository;
import com.auto.supplier.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@LoggingProfiler
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;

  @Autowired
  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  // Should not be secured
  public Optional<RoleEntity> findByUniqueName(String uniqueName) {
    return roleRepository.findByUniqueName(uniqueName);
  }
}
