package com.auto.supplier.commons.services;

import com.auto.supplier.entities.Entity;
import java.util.Collection;
import java.util.Optional;

public interface CrudServiceMediator<T extends Entity, ID> {
  boolean exist(ID var1);

  Collection<T> findAll();

  Optional<T> findById(ID var1);

  T create(T var1);

  T update(ID var1, T var2);

  T patch(ID var1, T var2);

  void delete(ID var1);
}

