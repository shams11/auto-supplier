package com.auto.supplier.repositories;

import com.auto.supplier.entities.UserEntity;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends GenericCrudRepository<UserEntity, UUID> {

  Optional<UserEntity> findByUsername(String username);
}
