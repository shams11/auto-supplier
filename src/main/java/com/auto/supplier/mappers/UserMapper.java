package com.auto.supplier.mappers;

import com.auto.supplier.commons.exceptions.ServiceException;
import com.auto.supplier.commons.mappers.ControllerMapper;
import com.auto.supplier.commons.models.MessageKey;
import com.auto.supplier.commons.models.Permission;
import com.auto.supplier.commons.models.Role;
import com.auto.supplier.entities.UserEntity;
import com.auto.supplier.models.User;
import com.auto.supplier.services.LoggedInUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class UserMapper implements ControllerMapper<UserEntity, User> {

  private static final int ACTIVE = 1;

  private final OrganizationMapper organizationMapper;
  private final LoggedInUserService loggedInUserService;

  @Autowired
  public UserMapper(OrganizationMapper organizationMapper, LoggedInUserService loggedInUserService) {
    this.organizationMapper = organizationMapper;
    this.loggedInUserService = loggedInUserService;
  }

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
    userEntity.setOrg(getLoggedInUser().getOrg());
    return userEntity;
  }

  private UserEntity getLoggedInUser() {
    return loggedInUserService.getUser().orElseThrow(() ->
            new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
                    .detailMessage("Org not present in LoggedIn user's context ")
                    .build());
  }

  @Override
  public User toPojo(UserEntity userEntity) {
    if(userEntity == null) {
      return null;
    }
    return User.builder()
        .id(userEntity.getId())
        .username(userEntity.getUsername())
        .active(userEntity.getActive())
        .email(userEntity.getEmail())
        .fname(userEntity.getFname())
        .lname(userEntity.getLname())
        .org(organizationMapper.toPojo(userEntity.getOrg()))
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
