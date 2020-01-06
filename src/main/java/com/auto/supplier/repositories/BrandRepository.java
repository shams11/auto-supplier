package com.auto.supplier.repositories;

import com.auto.supplier.entities.BrandEntity;
import com.auto.supplier.entities.OrgEntity;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BrandRepository extends GenericCrudRepository<BrandEntity, UUID> {

  Optional<BrandEntity> findByUniqueName(String name);

  List<BrandEntity> findByOrg(OrgEntity org);
}
