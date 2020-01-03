package com.auto.supplier.mappers;

import com.auto.supplier.commons.mappers.ControllerMapper;
import com.auto.supplier.entities.OrgEntity;
import com.auto.supplier.models.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper implements ControllerMapper<OrgEntity, Organization> {

  @Override
  public OrgEntity toEntity(Organization org) {

    if (org == null) {
      return null;
    }
    OrgEntity orgEntity = new OrgEntity();
    orgEntity.setUnique_name(org.getName());
    return orgEntity;
  }

  @Override
  public Organization toPojo(OrgEntity orgEntity) {
    if (orgEntity == null) {
      return null;
    }
    return Organization.builder()
            .id(orgEntity.getId().toString())
            .name(orgEntity.getUnique_name())
            .build();
  }
}
