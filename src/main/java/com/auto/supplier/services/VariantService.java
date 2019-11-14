package com.auto.supplier.services;

import com.auto.supplier.entities.VariantEntity;
import com.auto.supplier.models.Variant;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface VariantService {

  VariantEntity createVariant(UUID modelId, Variant variant);

  VariantEntity getVariantById(UUID id);

  VariantEntity getVariantByCode(String code);

  void delete(UUID id);

  VariantEntity updateVariant(UUID id, Variant variant);

  List<VariantEntity> getAllVariantsByModel(UUID modelId, Pageable pageable);
}
