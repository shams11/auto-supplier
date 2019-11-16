package com.auto.supplier.services.impl;

import com.auto.supplier.commons.exceptions.ServiceException;
import com.auto.supplier.commons.models.MessageKey;
import com.auto.supplier.commons.services.CrudServiceMediator;
import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.entities.PartEntity;
import com.auto.supplier.entities.VariantEntity;
import com.auto.supplier.mappers.PartMapper;
import com.auto.supplier.models.Part;
import com.auto.supplier.repositories.PartRepository;
import com.auto.supplier.repositories.VariantRepository;
import com.auto.supplier.services.PartService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PartServiceImpl implements PartService {

  private final VariantRepository variantRepository;
  private final PartRepository partRepository;
  private final CrudServiceMediator<PartEntity, UUID> mediator;
  private final PartMapper partMapper;

  @Autowired
  public PartServiceImpl(VariantRepository variantRepository,
                         PartRepository partRepository, PartMapper partMapper) {
    this.variantRepository = variantRepository;
    this.partRepository = partRepository;
    this.mediator = new CrudMediator<>(partRepository);
    this.partMapper = partMapper;
  }

  @Override
  @PreAuthorize("hasAuthority('CREATE_PART')")
  @Transactional
  public PartEntity createPart(UUID variantId, Part part) {
    validatePart(part);
    VariantEntity variantEntity = getVariantById(variantId);
    PartEntity partEntity = partMapper.toEntity(part);
    partEntity.setVariant(variantEntity);
    partEntity = partRepository.save(partEntity);
    return partEntity;
  }

  private void validatePart(Part part) {
    if (StringUtils.isEmpty(part.getCode()) || StringUtils.isEmpty(part.getName())
        || part.getVariantId() == null) {
      throw new ServiceException.Builder(MessageKey.BAD_REQUEST)
          .detailMessage("Part fields missing.")
          .build();
    }
  }

  private VariantEntity getVariantById(UUID id) {
    return variantRepository.findById(id).orElseThrow(() ->
        new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .args(id)
            .detailMessage(String.format("Variant not found for id %s", id))
            .build());
  }

  @Override
  @PreAuthorize("hasAuthority('READ_PART')")
  @Transactional
  public PartEntity getPartById(UUID id) {
    return partRepository.findById(id).orElseThrow(() ->
        new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .args(id)
            .detailMessage(String.format("Part not found for id %s", id))
            .build());
  }

  @Override
  @PreAuthorize("hasAuthority('READ_PART')")
  @Transactional
  public PartEntity getPartByCode(String code) {
    return partRepository.findByCode(code).orElseThrow(() ->
        new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .args(code)
            .detailMessage(String.format("Part not found for code %s", code))
            .build());
  }

  @Override
  @PreAuthorize("hasAuthority('DELETE_PART')")
  @Transactional
  public void delete(UUID id) {
    mediator.delete(id);
  }

  @Override
  @PreAuthorize("hasAuthority('UPDATE_PART')")
  @Transactional
  public PartEntity updatePart(UUID id, Part part) {
    validatePart(part);
    VariantEntity variantEntity = getVariantById(part.getVariantId());
    PartEntity partEntity = partMapper.toEntity(part);
    partEntity.setVariant(variantEntity);
    partEntity = mediator.patch(id, partEntity);
    return partEntity;
  }

  @Override
  @PreAuthorize("hasAuthority('READ_PART')")
  @Transactional
  public List<PartEntity> getAllPartsByVariant(UUID variantId) {
    return partRepository.findAllByVariantId(variantId);
  }
}
