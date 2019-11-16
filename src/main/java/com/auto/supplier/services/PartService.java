package com.auto.supplier.services;

import com.auto.supplier.entities.PartEntity;
import com.auto.supplier.models.Part;
import java.util.List;
import java.util.UUID;

public interface PartService {

  PartEntity createPart(UUID variantId, Part part);

  PartEntity getPartById(UUID id);

  PartEntity getPartByCode(String code);

  void delete(UUID id);

  PartEntity updatePart(UUID id, Part part);

  List<PartEntity> getAllPartsByVariant(UUID variantId);
}
