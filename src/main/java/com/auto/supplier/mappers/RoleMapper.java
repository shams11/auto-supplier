package com.auto.supplier.mappers;

import com.auto.supplier.commons.mappers.ControllerMapper;
import com.auto.supplier.commons.models.Permission;
import com.auto.supplier.commons.models.Role;
import com.auto.supplier.entities.RoleEntity;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class RoleMapper implements ControllerMapper<RoleEntity, Role> {
  @Override
  public RoleEntity toEntity(Role role) {
    if(role == null) {
      return null;
    }
    RoleEntity roleEntity = new RoleEntity();
    roleEntity.setUniqueName(role.getUniqueName());
    roleEntity.setDescription(role.getDescription());
    return roleEntity;
  }

  @Override
  public Role toPojo(RoleEntity roleEntity) {
    if(roleEntity == null) {
      return null;
    }
    return Role.builder()
        .id(roleEntity.getId().toString())
        .uniqueName(roleEntity.getUniqueName())
        .description(roleEntity.getDescription())
        .permissions(roleEntity.getPermissions().stream().map(permission -> Permission.builder()
            .id(permission.getId().toString())
            .uniqueName(permission.getUniqueName())
            .description(permission.getDescription())
            .build()).collect(Collectors.toSet()))
        .build();

  }
}
