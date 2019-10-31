package com.auto.supplier.repositories;

import com.auto.supplier.entities.ResetPasswordEntity;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResetPasswordRepository extends GenericCrudRepository<ResetPasswordEntity, UUID> {
   Optional<ResetPasswordEntity> findByResetToken(String resetToken);
}
