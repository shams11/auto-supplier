package com.auto.supplier.mappers;

import com.auto.supplier.commons.mappers.ControllerMapper;
import com.auto.supplier.entities.ProductionRecordEntity;
import com.auto.supplier.models.ProductionRecord;
import org.springframework.stereotype.Component;

@Component
public class ProductionDataMapper implements
    ControllerMapper<ProductionRecordEntity, ProductionRecord> {

  @Override
  public ProductionRecordEntity toEntity(ProductionRecord productionRecord) {
    ProductionRecordEntity productionRecordEntity = new ProductionRecordEntity();
    productionRecordEntity.setPartId(productionRecord.getPartId());
    productionRecordEntity.setVariantId(productionRecord.getVariantId());
    productionRecordEntity.populateProductionData(productionRecord.getProductionData());
    productionRecordEntity.setCompanyName("Dummy Company");
    productionRecordEntity.setUserCode("Dummy user code");
    return productionRecordEntity;
  }

  @Override
  public ProductionRecord toPojo(ProductionRecordEntity productionRecordEntity) {
    return ProductionRecord.builder()
            .id(productionRecordEntity.getId())
            .partId(productionRecordEntity.getPartId())
            .variantId(productionRecordEntity.getVariantId())
            .productionData(productionRecordEntity.productionDataAsList())
            .build();
  }
}
