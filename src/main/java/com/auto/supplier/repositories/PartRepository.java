package com.auto.supplier.repositories;

import com.auto.supplier.entities.PartEntity;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PartRepository extends GenericCrudRepository<PartEntity, UUID> {

  Optional<PartEntity> findByUniqueName(String name);

  Optional<PartEntity> findByCode(String code);

  List<PartEntity> findAllByVariantId(UUID variantId);
}
