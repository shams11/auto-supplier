package com.auto.supplier.services.impl;

import com.auto.supplier.commons.exceptions.ServiceException;
import com.auto.supplier.commons.models.MessageKey;
import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.entities.BrandEntity;
import com.auto.supplier.repositories.BrandRepository;
import com.auto.supplier.services.BrandService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@LoggingProfiler
@Service
@Slf4j
@Transactional(readOnly = true)
public class BrandServiceImpl implements BrandService {

  private final BrandRepository brandRepository;

  public BrandServiceImpl(BrandRepository brandRepository) {
    this.brandRepository = brandRepository;
  }

  @Override
  @PreAuthorize("hasAuthority('CREATE_BRAND')")
  @Transactional
  public BrandEntity createBrand(String name, MultipartFile logo) {

    brandRepository.findByName(name).orElseThrow(() ->
        new ServiceException.Builder(MessageKey.ENTITY_EXISTS)
            .args(name)
            .detailMessage(String.format("Brand logo with name %s already exists", name))
            .build());

    BrandEntity brandEntity = new BrandEntity();
    brandEntity.setUniqueName(name);
    brandEntity.setLogo(extractByteFromMultipartFile(logo));
    brandEntity.setLogoFileName(logo.getOriginalFilename());
    brandEntity = brandRepository.save(brandEntity);
    return brandEntity;
  }

  @Override
  public BrandEntity getLogoById(UUID id) {
    return brandRepository.findById(id).orElseThrow(() ->
        new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .args(id)
            .detailMessage(String.format("Brand logo not found for id ", id))
            .build());
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
