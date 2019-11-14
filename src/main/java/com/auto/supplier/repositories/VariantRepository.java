package com.auto.supplier.repositories;

import com.auto.supplier.entities.VariantEntity;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VariantRepository extends GenericCrudRepository<VariantEntity, UUID> {

  Optional<VariantEntity> findByUniqueName(String name);

  Optional<VariantEntity> findByCode(String code);

  List<VariantEntity> findAllByModelId(UUID modelId);
}
