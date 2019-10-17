package com.auto.supplier.services.impl;

import com.auto.supplier.commons.exceptions.ServiceException;
import com.auto.supplier.commons.models.MessageKey;
import com.auto.supplier.commons.services.CrudServiceMediator;
import com.auto.supplier.commons.utils.NullFieldsUtil;
import com.auto.supplier.entities.Entity;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.Optional;

@Transactional(readOnly = true)
public class CrudMediator<T extends Entity, ID>
    implements CrudServiceMediator<T, ID> {
  private final JpaRepository<T, ID> repository;

  public CrudMediator(JpaRepository<T, ID> repository) {
    this.repository = repository;
  }

  @Override
  public boolean exist(ID id) {
    return repository.existsById(id);
  }

  @Override
  public Collection<T> findAll() {
    return repository.findAll();
  }

  @Override
  public Optional<T> findById(ID id) {
    return repository.findById(id);
  }

  @Override
  @Transactional
  public T create(T entity) {
    if (entity.getId() != null) {
      throw new ServiceException.Builder(MessageKey.ILLEGAL_FIELD)
          .args(entity.getId())
          .build();
    }

    return repository.save(entity);
  }

  @Override
  @Transactional
  public T update(ID id, T entity) {
    validateIdFiled(id, entity);

    T savedEntity = repository.findById(id).orElseThrow(() ->
        new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .build());
    BeanUtils.copyProperties(entity, savedEntity, entity.getDoNotUpdateFields());

    return repository.save(savedEntity);
  }

  private void validateIdFiled(ID id, T entity) {
    if (id == null) {
      throw new ServiceException.Builder(MessageKey.MISSING_REQUIRED_FIELD)
          .args("id")
          .build();
    }

    if (entity.getId() != null) {
      throw new ServiceException.Builder(MessageKey.ILLEGAL_FIELD)
          .args(entity.getId())
          .build();
    }
  }

  @Override
  @Transactional
  public T patch(ID id, T entity) {
    validateIdFiled(id, entity);

    T savedEntity = repository.findById(id).orElseThrow(() ->
        new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .build());
    BeanUtils.copyProperties(entity, savedEntity, getIgnoreFieldsForPath(entity));
    return repository.save(savedEntity);
  }

  @Override
  @Transactional
  public void delete(ID id) {
    if (!exist(id)) {
      throw new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND).build();
    }
    repository.deleteById(id);
  }

  private String[] getIgnoreFieldsForPath(T entity) {
    // Do not update fields from BaseEntity interface
    String[] baseFields = entity.getDoNotUpdateFields();

    // Do not update null fields
    String[] nullFields = NullFieldsUtil.getNullPropertyNames(entity);

    return (String[])ArrayUtils.addAll(baseFields, nullFields);
  }
}
