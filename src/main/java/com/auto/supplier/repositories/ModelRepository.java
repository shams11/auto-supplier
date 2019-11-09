package com.auto.supplier.repositories;

import com.auto.supplier.entities.ModelEntity;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModelRepository extends GenericCrudRepository<ModelEntity, UUID> {
  Optional<ModelEntity> findByUniqueName(String name);
}
