package com.auto.supplier.mappers;

import com.auto.supplier.models.User;
import com.auto.supplier.commons.mappers.ControllerMapper;
import com.auto.supplier.commons.models.Permission;
import com.auto.supplier.commons.models.Role;
import com.auto.supplier.entities.UserEntity;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class UserMapper implements ControllerMapper<UserEntity, User> {

  private static final int ACTIVE = 1;

  @Override
  public UserEntity toEntity(User user) {
    if(user == null)
      return null;
    UserEntity userEntity = new UserEntity();
    userEntity.setActive(ACTIVE);
    userEntity.setEmail(user.getEmail());
    userEntity.setFname(user.getFname());
    userEntity.setLname(user.getLname());
    userEntity.setUsername(user.getUsername());
    return userEntity;
  }

  @Override
  public User toPojo(UserEntity userEntity) {
    if(userEntity == null) {
      return null;
    }
    return User.builder()
        .id(userEntity.getId())
        .active(userEntity.getActive())
        .email(userEntity.getEmail())
        .fname(userEntity.getFname())
        .lname(userEntity.getLname())
        .roles(userEntity.getRoles().stream().map(roleEntity -> Role.builder()
            .id(roleEntity.getId().toString())
            .uniqueName(roleEntity.getUniqueName())
            .description(roleEntity.getDescription())
            .permissions(roleEntity.getPermissions().stream().map(permission -> Permission.builder()
                .id(permission.getId().toString())
                .uniqueName(permission.getUniqueName())
                .description(permission.getDescription())
                .build()).collect(Collectors.toSet()))
            .build()).collect(Collectors.toSet()))
        .build();
  }
}
