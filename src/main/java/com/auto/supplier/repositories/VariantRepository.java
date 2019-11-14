package com.auto.supplier.repositories;

import com.auto.supplier.entities.VariantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VariantRepository extends GenericCrudRepository<VariantEntity, UUID> {

  Optional<VariantEntity> findByUniqueName(String name);

  Optional<VariantEntity> findByCode(String code);

  Page<VariantEntity> findAllByModelId(UUID model, Pageable pageable);
}
