package com.auto.supplier.repositories;

import com.auto.supplier.entities.ProductionDataEntity;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductionDataRepository  extends GenericCrudRepository<ProductionDataEntity, UUID> {

  Optional<ProductionDataEntity> findByPartId(UUID partId);

  Optional<ProductionDataEntity> findByVariantId(UUID variantId);
}
