package com.auto.supplier.mappers;

import com.auto.supplier.commons.mappers.ControllerMapper;
import com.auto.supplier.entities.ProductionDataEntity;
import com.auto.supplier.models.ProductionData;
import org.springframework.stereotype.Component;

@Component
public class ProductionDataMapper implements ControllerMapper<ProductionDataEntity, ProductionData> {

  @Override
  public ProductionDataEntity toEntity(ProductionData productionData) {
    ProductionDataEntity productionDataEntity = new ProductionDataEntity();
    productionDataEntity.setPartId(productionData.getPartId());
    productionDataEntity.setVariantId(productionData.getVariantId());
    productionDataEntity.populateDataFromMap(productionData.getData());
    return productionDataEntity;
  }

  @Override
  public ProductionData toPojo(ProductionDataEntity productionDataEntity) {
    return ProductionData.builder()
            .id(productionDataEntity.getId())
            .partId(productionDataEntity.getPartId())
            .variantId(productionDataEntity.getVariantId())
            .data(productionDataEntity.filterAsMap())
            .build();
  }
}
