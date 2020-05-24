package com.auto.supplier.repositories;

import com.auto.supplier.entities.ProductionRecordEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface ProductionDataRepository
    extends GenericCrudRepository<ProductionRecordEntity, UUID> {

  Optional<ProductionRecordEntity> findByPartId(UUID partId);

  Optional<ProductionRecordEntity> findByVariantId(UUID variantId);
}
