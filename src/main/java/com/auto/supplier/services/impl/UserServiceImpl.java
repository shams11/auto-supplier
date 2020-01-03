package com.auto.supplier.services.impl;

import com.auto.supplier.commons.exceptions.ServiceException;
import com.auto.supplier.commons.models.MessageKey;
import com.auto.supplier.commons.models.Role;
import com.auto.supplier.commons.services.CrudServiceMediator;
import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.entities.ResetPasswordEntity;
import com.auto.supplier.entities.RoleEntity;
import com.auto.supplier.entities.UserEntity;
import com.auto.supplier.mappers.UserMapper;
import com.auto.supplier.models.User;
import com.auto.supplier.repositories.ResetPasswordRepository;
import com.auto.supplier.repositories.UserRepository;
import com.auto.supplier.services.MailService;
import com.auto.supplier.services.RoleService;
import com.auto.supplier.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@LoggingProfiler
@Service
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

  // default hash for password "welcome1a"
  private static final String DEFAULT_PASSWORD = "$2a$04$60WnjxGpXS8zlH7w3W0Sk" +
      ".D3YNJA7zWU8iuLug8HpCSWnjXvrQVoS";
  private static final String RESET_PASSWORD_API_PATH = "/v1/users/reset-password";
  private final UserRepository userRepository;
  private final ResetPasswordRepository resetPasswordRepository;
  private final UserMapper userMapper;
  private final CrudServiceMediator<UserEntity, UUID> mediator;
  private final RoleService roleService;
  private final MailService mailService;
  private final CrudServiceMediator<ResetPasswordEntity, UUID> resetPasswordMediator;



  @Autowired
  public UserServiceImpl(UserRepository userRepository,
                         UserMapper userMapper,
                         RoleService roleService,
                         MailService mailService,
                         ResetPasswordRepository resetPasswordRepository) {
    this.userRepository = userRepository;
    this.resetPasswordRepository = resetPasswordRepository;
    this.userMapper = userMapper;
    this.roleService = roleService;
    this.mediator = new CrudMediator<>(userRepository);
    this.mailService = mailService;
    this.resetPasswordMediator = new CrudMediator<>(resetPasswordRepository);
  }

  @Override
  @PreAuthorize("hasAuthority('CREATE_USER')")
  @Transactional
  public UserEntity create(User user, HttpServletRequest httpServletRequest)
      throws MalformedURLException {
    validateUserDetails(user);
    UserEntity userEntity = userMapper.toEntity(user);
    associateRoles(userEntity, user.getRoles());
    setDefaultPassword(userEntity);
    userEntity = mediator.create(userEntity);
    //sendActivationMail with reset password link
    String resetToken = UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
    String resetPasswordLink = buildResetPasswordLink(httpServletRequest, resetToken);
    ResetPasswordEntity resetPasswordEntity = buildResetPasswordEntity(userEntity.getEmail(),
        resetToken);
    resetPasswordMediator.create(resetPasswordEntity);
    mailService.sendMail(user, resetPasswordLink);
    return userEntity;
  }

  private ResetPasswordEntity buildResetPasswordEntity(String email, String resetToken) {
    ResetPasswordEntity resetPasswordEntity = new ResetPasswordEntity();
    resetPasswordEntity.setEmail(email);
    resetPasswordEntity.setResetToken(resetToken);
    return resetPasswordEntity;
  }

  private String buildResetPasswordLink(HttpServletRequest httpServletRequest, String resetToken)
      throws MalformedURLException {
    URL url = new URL(httpServletRequest.getRequestURL().toString());
    return String.format("%s://%s",
        url.getProtocol(),
        url.getAuthority())
        .concat(RESET_PASSWORD_API_PATH)
        .concat("?token=")
        .concat(resetToken);
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
  public UserEntity getUserByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(() ->
        new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .args(email)
            .detailMessage(String.format("User not found for user email ", email))
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
    validateEmptyFields(user);

    usernameExists(user.getUsername());
    emailExists(user.getEmail());
  }

  private void validateEmptyFields(User user) {
    if (StringUtils.isEmpty(user.getUsername()) ||
        StringUtils.isEmpty(user.getEmail()) ||
        StringUtils.isEmpty(user.getFname()) ||
        StringUtils.isEmpty(user.getLname())) {
      throw new ServiceException.Builder(MessageKey.BAD_REQUEST)
          .args(user.getUsername())
          .detailMessage(String.format("Empty input fields for username ",
              user.getUsername()))
          .build();
    }
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
    if (CollectionUtils.isEmpty(roles)) {
      userEntity.setRoles(Set.of(findRole("LEAD")));
      return;
    }
    roles.forEach(role -> findAndAddRole(userEntity, role.getUniqueName()));
  }

  private void findAndAddRole(UserEntity userEntity, String roleName) {
    RoleEntity roleEntity = findRole(roleName);
    userEntity.addRole(roleEntity);
  }

  private RoleEntity findRole(String roleName) {
    return roleService
            .findByUniqueName(roleName)
            .orElseThrow(() -> new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
                    .detailMessage("Could not find role to be associated with the user")
                    .build());
  }


  @Override
  @PreAuthorize("hasAuthority('UPDATE_USER')")
  @Transactional
  public UserEntity update(UUID id, User user) {

    validateEmptyFields(user);
    UserEntity userEntity = userMapper.toEntity(user);
    UserEntity savedEntity = userRepository.findById(id).orElseThrow(() ->
        new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .build());
    String savedPassword = savedEntity.getPassword();
    BeanUtils.copyProperties(userEntity, savedEntity, userEntity.getDoNotUpdateFields());
    // TODO: discuss whether allow admin to update any user's password ?
    savedEntity.setPassword(savedPassword);
    return userRepository.save(savedEntity);
  }

  @Override
  @Transactional
  public void resetPassword(String resetToken,
                            String newPassword) {
    validateResetPasswordInput(resetToken, newPassword);
    resetPasswordRepository
        .findByResetToken(resetToken)
        .ifPresentOrElse(
            resetPasswordEntity -> updateNewPassword(resetPasswordEntity, newPassword),
            () -> handleResetTokenNotFound(resetToken));
  }

  @Override
  public boolean isResetTokenValid(String resetToken) {
    if (StringUtils.isEmpty(resetToken)) {
      throw new ServiceException.Builder(MessageKey.BAD_REQUEST)
          .detailMessage("reset token must be present.")
          .build();
    }
    return resetPasswordRepository
        .findByResetToken(resetToken)
        .isPresent();
  }

  private void handleResetTokenNotFound(String resetToken) {
    throw new ServiceException.Builder(MessageKey.TOKEN_EXPIRED)
        .args(resetToken)
        .detailMessage(String.format("Token %s is expired : ", resetToken))
        .build();
  }

  private void updateNewPassword(ResetPasswordEntity resetPasswordEntity,
                                 String newPassword) {
    UserEntity userEntity = userRepository.findByEmail(resetPasswordEntity.getEmail())
        .orElseThrow(() -> new ServiceException.Builder(MessageKey.ENTITY_NOT_FOUND)
            .detailMessage(String.format("Could not find user with email %s ",
                resetPasswordEntity.getEmail()))
            .build());
    userEntity.setPassword(new BCryptPasswordEncoder().encode((newPassword)));
    userRepository.save(userEntity);
    resetPasswordMediator.delete(resetPasswordEntity.getId());
  }

  private void validateResetPasswordInput(String resetToken,
                                          String password) {
    if (StringUtils.isEmpty(resetToken)) {
        throw new ServiceException.Builder(MessageKey.BAD_REQUEST)
            .detailMessage("reset token must be present.")
            .build();
      }

    if (StringUtils.isEmpty(password)) {
      throw new ServiceException.Builder(MessageKey.BAD_REQUEST)
          .detailMessage("password must be present.")
          .build();
    }
  }

  @Override
  @PreAuthorize("hasAuthority('DELETE_USER')")
  @Transactional
  public void delete(UUID id) {
    mediator.delete(id);
  }
}
