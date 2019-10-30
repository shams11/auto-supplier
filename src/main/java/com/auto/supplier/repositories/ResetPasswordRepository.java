package com.auto.supplier.repositories;

import com.auto.supplier.entities.ResetPasswordEntity;
import java.util.Optional;
import java.util.UUID;

public interface ResetPasswordRepository extends GenericCrudRepository<ResetPasswordEntity, UUID> {
   Optional<ResetPasswordEntity> findByResetToken(String resetToken);
}
