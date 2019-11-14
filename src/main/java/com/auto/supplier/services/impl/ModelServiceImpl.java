package com.auto.supplier.services.impl;

import com.auto.supplier.commons.exceptions.ServiceException;
import com.auto.supplier.commons.models.MessageKey;
import com.auto.supplier.commons.services.CrudServiceMediator;
import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.entities.BrandEntity;
import com.auto.supplier.entities.ModelEntity;
import com.auto.supplier.repositories.BrandRepository;
import com.auto.supplier.repositories.ModelRepository;
import com.auto.supplier.services.ModelService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@LoggingProfiler
@Service
@Slf4j
@Transactional(readOnly = true)
public class ModelServiceImpl implements ModelService {

  private final ModelRepository modelRepository;
  private final BrandRepository brandRepository;
  private final CrudServiceMediator<ModelEntity, UUID> mediator;

  public ModelServiceImpl(ModelRepository modelRepository, BrandRepository brandRepository) {
    this.modelRepository = modelRepository;
    this.mediator = new CrudMediator<>(modelRepository);
    this.brandRepository = brandRepository;
  }

  @Override
  @PreAuthorize("hasAuthority('CREATE_MODEL')")
  @Transactional
  public ModelEntity createModel(UUID brandId, String modelName, MultipartFile logo) {
    validate(modelName);
    BrandEntity brandEntity = getBrandById(brandId);
    ModelEntity modelEntity = new ModelEntity();
    modelEntity.setUniqueName(modelName);
    modelEntity.setModelLogo(extractByteFromMultipartFile(logo));
    modelEntity.setModelLogoFileName(logo.getOriginalFilename());
    modelEntity.setBrand(brandEntity);
    modelEntity = modelRepository.save(modelEntity);
    return modelEntity;
  }

  private BrandEntity getBrandById(UUID brandId) {
    return brandRepository.findById(brandId).orElseThrow(() ->
        new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .args(brandId)
            .detailMessage(String.format("Brand entity not found for id %s", brandId))
            .build());
  }

  private void validate(String modelName) {
    modelRepository.findByUniqueName(modelName).ifPresent(u -> {
      throw new ServiceException.Builder(MessageKey.ENTITY_EXISTS)
          .args(modelName)
          .detailMessage(String.format("Model logo with name %s already exists", modelName))
          .build();
    });
  }

  @Override
  @PreAuthorize("hasAuthority('READ_MODEL')")
  public ModelEntity getModelById(UUID id) {
    return modelRepository.findById(id).orElseThrow(() ->
        new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .args(id)
            .detailMessage(String.format("Model logo not found for id %s", id))
            .build());
  }

  @Override
  @PreAuthorize("hasAuthority('DELETE_MODEL')")
  @Transactional
  public void delete(UUID id) {
    mediator.delete(id);
  }

  @Override
  @PreAuthorize("hasAuthority('UPDATE_MODEL')")
  @Transactional
  public ModelEntity updateModel(UUID id, String name, MultipartFile logo, UUID brandId) {
    if (StringUtils.isEmpty(name) || logo == null) {
      throw new ServiceException.Builder(MessageKey.BAD_REQUEST)
          .detailMessage("Invalid input params")
          .build();
    }
    BrandEntity brandEntity = getBrandById(brandId);
    ModelEntity modelEntity = new ModelEntity();
    modelEntity.setModelLogoFileName(logo.getOriginalFilename());
    modelEntity.setModelLogo(extractByteFromMultipartFile(logo));
    modelEntity.setUniqueName(name);
    modelEntity.setBrand(brandEntity);
    modelEntity = mediator.patch(id, modelEntity);
    return modelEntity;
  }

  private byte[] extractByteFromMultipartFile(MultipartFile logo) {
    try {
      return logo.getBytes();
    } catch (IOException e) {
      throw new ServiceException.Builder(MessageKey.BAD_REQUEST)
          .detailMessage("Invalid log payload")
          .build();
    }
  }
}
