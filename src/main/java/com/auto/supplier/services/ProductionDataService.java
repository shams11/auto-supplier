package com.auto.supplier.services;

import com.auto.supplier.entities.ProductionRecordEntity;
import java.util.UUID;

public interface ProductionDataService {

  ProductionRecordEntity createProductionData(ProductionRecordEntity productionRecordEntity);

  ProductionRecordEntity findById(UUID uuid);
}
