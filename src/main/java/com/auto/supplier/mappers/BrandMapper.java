package com.auto.supplier.mappers;

import com.auto.supplier.commons.mappers.ControllerMapper;
import com.auto.supplier.entities.BrandEntity;
import com.auto.supplier.models.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper implements ControllerMapper<BrandEntity, Brand> {

  @Override
  public BrandEntity toEntity(Brand brand) {
    if (brand == null) {
      return null;
    }
    BrandEntity brandEntity = new BrandEntity();
    brandEntity.setUniqueName(brand.getName());
    brandEntity.setLogoFileName(brand.getLogoFileName());
    brandEntity.setLogo(brand.getLogo());
    return brandEntity;
  }

  @Override
  public Brand toPojo(BrandEntity brandEntity) {
    if (brandEntity == null) {
      return null;
    }
    return Brand.builder()
        .id(brandEntity.getId().toString())
        .name(brandEntity.getUniqueName())
        .logoFileName(brandEntity.getLogoFileName())
        .logo(brandEntity.getLogo())
        .build();
  }
}
