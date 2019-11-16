package com.auto.supplier.mappers;

import com.auto.supplier.commons.mappers.ControllerMapper;
import com.auto.supplier.entities.PartEntity;
import com.auto.supplier.models.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PartMapper implements ControllerMapper<PartEntity, Part> {


  @Override
  public PartEntity toEntity(Part part) {
    if (part == null) {
      return null;
    }
    PartEntity partEntity = new PartEntity();
    partEntity.setId(part.getId());
    partEntity.setCode(part.getCode());
    partEntity.setUniqueName(part.getName());
    partEntity.setDescription(part.getDescription());
    return partEntity;
  }

  @Override
  public Part toPojo(PartEntity partEntity) {
    if (partEntity == null) {
      return null;
    }
    return Part.builder()
        .id(partEntity.getId())
        .name(partEntity.getUniqueName())
        .code(partEntity.getCode())
        .description(partEntity.getDescription())
        .variantId(partEntity.getVariant().getId())
        .build();
  }
}
