package com.auto.supplier.services;

import com.auto.supplier.entities.ProductionDataEntity;
import com.auto.supplier.models.ProductionData;
import java.util.UUID;

public interface ProductionDataService {

  ProductionDataEntity createProductionData(ProductionData productionData);

  ProductionDataEntity findById(UUID uuid);
}
