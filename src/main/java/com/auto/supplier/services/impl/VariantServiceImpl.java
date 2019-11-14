package com.auto.supplier.services.impl;

import com.auto.supplier.commons.exceptions.ServiceException;
import com.auto.supplier.commons.models.MessageKey;
import com.auto.supplier.commons.services.CrudServiceMediator;
import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.entities.ModelEntity;
import com.auto.supplier.entities.VariantEntity;
import com.auto.supplier.mappers.VariantMapper;
import com.auto.supplier.models.Variant;
import com.auto.supplier.repositories.ModelRepository;
import com.auto.supplier.repositories.VariantRepository;
import com.auto.supplier.services.VariantService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@LoggingProfiler
@Service
@Slf4j
@Transactional(readOnly = true)
public class VariantServiceImpl implements VariantService {

  private final VariantRepository variantRepository;
  private final ModelRepository modelRepository;
  private final CrudServiceMediator<VariantEntity, UUID> mediator;
  private final VariantMapper variantMapper;

  public VariantServiceImpl(VariantRepository variantRepository,
                            ModelRepository modelRepository, VariantMapper variantMapper) {
    this.variantRepository = variantRepository;
    this.modelRepository = modelRepository;
    this.mediator = new CrudMediator<>(variantRepository);
    this.variantMapper = variantMapper;
  }

  @Override
  @PreAuthorize("hasAuthority('CREATE_VARIANT')")
  @Transactional
  public VariantEntity createVariant(UUID modelId, Variant variant) {
    validateVariant(variant);
    ModelEntity modelEntity = getModelById(modelId);
    VariantEntity variantEntity = variantMapper.toEntity(variant);
    variantEntity.setModel(modelEntity);
    variantEntity = variantRepository.save(variantEntity);
    return variantEntity;
  }

  private void validateVariant(Variant variant) {
    validateInputParams(variant);
    variantRepository.findByCode(variant.getCode()).ifPresent(u -> {
      throw new ServiceException.Builder(MessageKey.ENTITY_EXISTS)
          .args(variant.getCode())
          .detailMessage(String.format("Variant already exists for code %s", variant.getCode()))
          .build();
    });

    variantRepository.findByUniqueName(variant.getName()).ifPresent(u -> {
      throw new ServiceException.Builder(MessageKey.ENTITY_EXISTS)
          .args(variant.getCode())
          .detailMessage(String.format("Variant already exists for name %s", variant.getName()))
          .build();
    });
  }

  private void validateInputParams(Variant variant) {
    if (StringUtils.isEmpty(variant.getCode()) || StringUtils.isEmpty(variant.getName())
        || variant.getModelId() == null) {
      throw new ServiceException.Builder(MessageKey.BAD_REQUEST)
          .detailMessage("Variant fields missing.")
          .build();
    }
  }

  private ModelEntity getModelById(UUID id) {
    return modelRepository.findById(id).orElseThrow(() ->
        new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .args(id)
            .detailMessage(String.format("Model not found for id %s", id))
            .build());
  }

  @Override
  @PreAuthorize("hasAuthority('READ_VARIANT')")
  @Transactional
  public VariantEntity getVariantById(UUID id) {
    return variantRepository.findById(id).orElseThrow(() ->
        new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .args(id)
            .detailMessage(String.format("Variant not found for id %s", id))
            .build());
  }

  @Override
  @PreAuthorize("hasAuthority('READ_VARIANT')")
  @Transactional
  public List<VariantEntity> getAllVariantsByModel(UUID modelId, Pageable pageable) {
    return null;
  }

  @Override
  @PreAuthorize("hasAuthority('READ_VARIANT')")
  @Transactional
  public VariantEntity getVariantByCode(String code) {
    return variantRepository.findByCode(code).orElseThrow(() ->
        new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .args(code)
            .detailMessage(String.format("Variant not found for code %s", code))
            .build());
  }

  @Override
  @PreAuthorize("hasAuthority('DELETE_VARIANT')")
  @Transactional
  public void delete(UUID id) {
    mediator.delete(id);
  }

  @Override
  @PreAuthorize("hasAuthority('UPDATE_VARIANT')")
  @Transactional
  public VariantEntity updateVariant(UUID id, Variant variant) {
    validateInputParams(variant);
    ModelEntity modelEntity = getModelById(variant.getModelId());
    VariantEntity variantEntity = variantMapper.toEntity(variant);
    variantEntity.setModel(modelEntity);
    variantEntity = mediator.patch(id, variantEntity);
    return variantEntity;
  }
}
