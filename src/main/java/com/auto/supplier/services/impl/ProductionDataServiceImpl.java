package com.auto.supplier.services.impl;

import com.auto.supplier.commons.exceptions.ServiceException;
import com.auto.supplier.commons.models.MessageKey;
import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.entities.ProductionRecordEntity;
import com.auto.supplier.repositories.PartRepository;
import com.auto.supplier.repositories.ProductionDataRepository;
import com.auto.supplier.repositories.VariantRepository;
import com.auto.supplier.services.ProductionDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@LoggingProfiler
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductionDataServiceImpl implements ProductionDataService {

  private final ProductionDataRepository productionDataRepository;
  private final PartRepository partRepository;
  private final VariantRepository variantRepository;

  @Override
  @Transactional
  public ProductionRecordEntity createProductionData(
      ProductionRecordEntity productionRecordEntity) {
    validateInputParams(productionRecordEntity);
    return productionDataRepository.save(productionRecordEntity);
  }

  @Override
  public ProductionRecordEntity findById(UUID uuid) {
    return productionDataRepository.findById(uuid).orElseThrow(() ->
        new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .args(uuid)
            .detailMessage(String.format("Production data record not found for id %s", uuid))
            .build());
  }

  private void validateInputParams(ProductionRecordEntity productionRecordEntity) {
    partRepository.findById(productionRecordEntity.getPartId()).orElseThrow(() ->
        new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .args(productionRecordEntity.getPartId())
            .detailMessage(String.format("Part not found for id %s",
                productionRecordEntity.getPartId()))
            .build());

    variantRepository.findById(productionRecordEntity.getVariantId()).orElseThrow(() ->
        new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .args(productionRecordEntity.getPartId())
            .detailMessage(String.format("Variant not found for id %s",
                productionRecordEntity.getVariantId()))
            .build());
  }
}
