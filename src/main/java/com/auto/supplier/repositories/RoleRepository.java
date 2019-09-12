package com.auto.supplier.repositories;

import com.auto.supplier.entities.RoleEntity;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends GenericCrudRepository<RoleEntity, UUID> {
  Optional<RoleEntity> findByUniqueName(String uniqueName);
}
