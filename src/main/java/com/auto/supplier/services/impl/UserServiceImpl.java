package com.auto.supplier.services.impl;

import com.auto.supplier.commons.services.CrudServiceMediator;
import com.auto.supplier.models.User;
import com.auto.supplier.commons.exceptions.ServiceException;
import com.auto.supplier.commons.models.MessageKey;
import com.auto.supplier.commons.models.Role;
import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.entities.RoleEntity;
import com.auto.supplier.entities.UserEntity;
import com.auto.supplier.mappers.UserMapper;
import com.auto.supplier.repositories.UserRepository;
import com.auto.supplier.services.RoleService;
import com.auto.supplier.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@LoggingProfiler
@Service
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

  private static final String DEFAULT_PASSWORD = "$2a$04$60WnjxGpXS8zlH7w3W0Sk.D3YNJA7zWU8iuLug8HpCSWnjXvrQVoS";
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final CrudServiceMediator<UserEntity, UUID> mediator;
  private final RoleService roleService;

  @Autowired
  public UserServiceImpl(UserRepository userRepository,
                         UserMapper userMapper,
                         RoleService roleService
  ) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.roleService = roleService;
    this.mediator = new CrudMediator<>(userRepository);
  }

  @Override
  @PreAuthorize("hasAuthority('ADMIN')")
  @Transactional
  public UserEntity createUser(User user) {
    validateUserDetails(user);
    UserEntity userEntity = userMapper.toEntity(user);
    associateRoles(userEntity, user.getRoles());
    setDefaultPassword(userEntity);
    userEntity = mediator.create(userEntity);
    //sendActivationMail with reset password link
    return userEntity;
  }

  private void setDefaultPassword(UserEntity userEntity) {
    userEntity.setPassword(DEFAULT_PASSWORD);
  }

  @Override
  public UserEntity getUserByUsername(String username) {
    return userRepository.findByUsername(username).orElseThrow(() ->
        new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .args(username)
            .detailMessage(String.format("User not found for user name ", username))
            .build());
  }

  @Override
  public List<UserEntity> getAllUsers() {
    return userRepository.findAll();
  }

  private void validateUserDetails(User user) {
    if (user.getId() != null) {
      throw new ServiceException.Builder(MessageKey.ILLEGAL_FIELD)
          .args(user.getUsername())
          .detailMessage("Id should be null while creating userEntity")
          .build();
    }
    if (StringUtils.isEmpty(user.getUsername()) &&
        StringUtils.isEmpty(user.getEmail()) &&
        StringUtils.isEmpty(user.getFname()) &&
        StringUtils.isEmpty(user.getLname())) {
      throw new ServiceException.Builder(MessageKey.BAD_REQUEST)
          .args(user.getUsername())
          .detailMessage(String.format("Empty input fields for username ",
              user.getUsername()))
          .build();
    }

    usernameExists(user.getUsername());
    emailExists(user.getEmail());
  }

  private void usernameExists(String username) {
    userRepository.findByUsername(username).ifPresent(u -> {
      throw new ServiceException.Builder(MessageKey.ENTITY_EXISTS)
          .args(username)
          .detailMessage(String.format("User with username %s already present", username))
          .build();
    });
  }

  private void emailExists(String email) {
    userRepository.findByEmail(email).ifPresent(u -> {
      throw new ServiceException.Builder(MessageKey.ENTITY_EXISTS)
          .args(email)
          .detailMessage(String.format("User with email %s already present", email))
          .build();
    });
  }

  private void associateRoles(UserEntity userEntity, Set<Role> roles) {
    if(CollectionUtils.isEmpty(roles)) {
      throw new ServiceException.Builder(MessageKey.ILLEGAL_FIELD)
          .args("roles")
          .detailMessage(String.format("Role is empty for username " ,
              userEntity.getUsername()))
          .build();
    }
    roles.forEach(role -> findAndAddRole(userEntity, role.getUniqueName()));
  }


  private void findAndAddRole(UserEntity userEntity, String roleName) {
    RoleEntity roleEntity = roleService
        .findByUniqueName(roleName)
        .orElseThrow(() -> new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .detailMessage("Could not find role to be associated with the user")
            .build());
    userEntity.addRole(roleEntity);
  }

  @Override
  @Transactional
  public void deleteOrg(UUID id) {
    mediator.delete(id);
  }
}
