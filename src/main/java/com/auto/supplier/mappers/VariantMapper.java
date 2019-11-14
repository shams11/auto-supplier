package com.auto.supplier.mappers;

import com.auto.supplier.commons.mappers.ControllerMapper;
import com.auto.supplier.entities.VariantEntity;
import com.auto.supplier.models.Variant;
import org.springframework.stereotype.Component;

@Component
public class VariantMapper implements ControllerMapper<VariantEntity, Variant> {

  @Override
  public VariantEntity toEntity(Variant variant) {
    if (variant == null) {
      return null;
    }
    VariantEntity variantEntity = new VariantEntity();
    variantEntity.setUniqueName(variant.getName());
    variantEntity.setCode(variant.getCode());
    variantEntity.setDescription(variant.getDescription());
    return variantEntity;
  }

  @Override
  public Variant toPojo(VariantEntity variantEntity) {
    if (variantEntity == null) {
      return null;
    }
    return Variant.builder()
        .id(variantEntity.getId())
        .modelId(variantEntity.getModel().getId())
        .code(variantEntity.getCode())
        .name(variantEntity.getUniqueName())
        .description(variantEntity.getDescription())
        .build();
  }
}
