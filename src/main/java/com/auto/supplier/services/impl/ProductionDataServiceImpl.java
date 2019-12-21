package com.auto.supplier.services.impl;

import com.auto.supplier.commons.exceptions.ServiceException;
import com.auto.supplier.commons.models.MessageKey;
import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.entities.ProductionDataEntity;
import com.auto.supplier.mappers.ProductionDataMapper;
import com.auto.supplier.models.ProductionData;
import com.auto.supplier.repositories.PartRepository;
import com.auto.supplier.repositories.ProductionDataRepository;
import com.auto.supplier.repositories.VariantRepository;
import com.auto.supplier.services.ProductionDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@LoggingProfiler
@Service
@Slf4j
@Transactional(readOnly = true)
public class ProductionDataServiceImpl implements ProductionDataService {

  private final ProductionDataRepository productionDataRepository;
  private final ProductionDataMapper productionDataMapper;
  private final PartRepository partRepository;
  private final VariantRepository variantRepository;

  public ProductionDataServiceImpl(ProductionDataRepository productionDataRepository,
                                   ProductionDataMapper productionDataMapper, PartRepository partRepository, VariantRepository variantRepository) {
    this.productionDataRepository = productionDataRepository;
    this.productionDataMapper = productionDataMapper;
    this.partRepository = partRepository;
    this.variantRepository = variantRepository;
  }

  @Override
  @Transactional
  public ProductionDataEntity createProductionData(ProductionData productionData) {
    validateInputParams(productionData);
    ProductionDataEntity productionDataEntity = productionDataMapper.toEntity(productionData);
    return productionDataRepository.save(productionDataEntity);
  }

  @Override
  public ProductionDataEntity findById(UUID uuid) {
    return productionDataRepository.findById(uuid).orElseThrow(() ->
            new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
                    .args(uuid)
                    .detailMessage(String.format("Production data record not found for id %s", uuid))
                    .build());
  }

  private void validateInputParams(ProductionData productionData) {
    partRepository.findById(productionData.getPartId()).orElseThrow(() ->
            new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
                    .args(productionData.getPartId())
                    .detailMessage(String.format("Part not found for id %s", productionData.getPartId()))
                    .build());

    variantRepository.findById(productionData.getVariantId()).orElseThrow(() ->
            new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
                    .args(productionData.getPartId())
                    .detailMessage(String.format("Variant not found for id %s", productionData.getVariantId()))
                    .build());
  }
}
