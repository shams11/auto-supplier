package com.auto.supplier.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface GenericCrudRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
  List<T> findAll();
}
