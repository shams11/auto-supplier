package com.auto.supplier.services.impl;

import com.auto.supplier.commons.exceptions.ServiceException;
import com.auto.supplier.commons.models.MessageKey;
import com.auto.supplier.commons.services.CrudServiceMediator;
import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.entities.BrandEntity;
import com.auto.supplier.entities.UserEntity;
import com.auto.supplier.repositories.BrandRepository;
import com.auto.supplier.services.BrandService;
import com.auto.supplier.services.LoggedInUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@LoggingProfiler
@Service
@Slf4j
@Transactional(readOnly = true)
public class BrandServiceImpl implements BrandService {

  private final BrandRepository brandRepository;
  private final CrudServiceMediator<BrandEntity, UUID> mediator;
  private final LoggedInUserService loggedInUserService;

  public BrandServiceImpl(BrandRepository brandRepository, LoggedInUserService loggedInUserService) {
    this.brandRepository = brandRepository;
    this.mediator = new CrudMediator<>(brandRepository);
    this.loggedInUserService = loggedInUserService;
  }

  @Override
  @PreAuthorize("hasAuthority('CREATE_BRAND')")
  @Transactional
  public BrandEntity createBrand(String name, MultipartFile logo) {

    brandRepository.findByUniqueName(name).ifPresent(u -> {
      throw new ServiceException.Builder(MessageKey.ENTITY_EXISTS)
          .args(name)
          .detailMessage(String.format("Brand logo with name %s already exists", name))
          .build();
    });

    BrandEntity brandEntity = new BrandEntity();
    brandEntity.setUniqueName(name);
    brandEntity.setLogo(extractByteFromMultipartFile(logo));
    brandEntity.setLogoFileName(logo.getOriginalFilename());
    brandEntity.setOrg(getLoggedInUser().getOrg());
    brandEntity = brandRepository.save(brandEntity);
    return brandEntity;
  }

  private UserEntity getLoggedInUser() {
    return loggedInUserService.getUser().orElseThrow(() ->
            new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
                    .detailMessage("Org not present in LoggedIn user's context ")
                    .build());
  }

  @Override
  @PreAuthorize("hasAuthority('READ_BRAND')")
  public BrandEntity getLogoById(UUID id) {
    return brandRepository.findById(id).orElseThrow(() ->
        new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .args(id)
            .detailMessage(String.format("Brand logo not found for id %s ", id))
            .build());
  }

  @Override
  @PreAuthorize("hasAuthority('DELETE_BRAND')")
  @Transactional
  public void delete(UUID id) {
    mediator.delete(id);
  }

  @Override
  @PreAuthorize("hasAuthority('UPDATE_BRAND')")
  @Transactional
  public BrandEntity updateBrand(UUID id, String name, MultipartFile logo) {
    BrandEntity brandEntity = new BrandEntity();
    brandEntity.setLogoFileName(logo.getOriginalFilename());
    brandEntity.setLogo(extractByteFromMultipartFile(logo));
    brandEntity.setUniqueName(name);
    brandEntity = mediator.patch(id, brandEntity);
    return brandEntity;
  }

  @Override
  @PreAuthorize("hasAuthority('READ_BRAND')")
  public List<BrandEntity> getAllBrands(UUID orgId) {
    UserEntity userEntity = getLoggedInUser();
    return brandRepository.findByOrg(userEntity.getOrg());
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
