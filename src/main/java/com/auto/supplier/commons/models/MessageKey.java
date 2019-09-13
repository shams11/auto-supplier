package com.auto.supplier.commons.models;

import static com.auto.supplier.commons.models.ExceptionLogLevel.ERROR;
import static com.auto.supplier.commons.models.ExceptionLogLevel.DEBUG;

public enum MessageKey {
  ENTITY_NOT_FOUND("The requested resource is not found"),
  SERVER_TO_SERVER_TOKEN_ISSUE("Internal Server Error", ERROR),
  CLIENT_TO_SERVER_TOKEN_ISSUE("Internal Server Error", ERROR),
  INVALID_EMAIL_FORMAT("Email format is invalid"),
  INVALID_INVITE_TOKEN_FORMAT("Supplier invite token format is invalid"),
  USER_INVALID_CREDENTIALS("Invalid Username/Password Pair"),
  USER_UNAUTHORIZED("User is not authorized"),
  TOKEN_INVALID("The provided token is invalid"),
  TOKEN_EXPIRED("The provided token has expired"),
  OFFICE365_CONNECTION_ERROR("There was a problem while trying to connect to o365", ERROR),
  ACCOUNT_LOCKED("Account locked for User"),
  PASSWORD_RESET_MOST_RECENT_ERROR("%s", true),
  USER_NOT_INVITED("The user is not invited by buyer"),
  USER_EXISTS("The user already exists"),
  CREATE_ACCOUNT_API_ISSUE("The account cannot be created", ERROR),
  S4_MIGRATE_ACCOUNT_API_ISSUE("Could not get user account information", ERROR),
  EXTERNAL_SERVER_CONNECTION_ISSUE("Internal Server Error", ERROR),
  EXTERNAL_SERVER_API_ISSUE("Internal Server Error", ERROR),
  BAD_REQUEST("Bad request"),
  INTERNAL_SERVER_ERROR("Internal Server error"),
  ENTITY_EXISTS("The entity with name %s already exists.", true),
  NOT_IMPLEMENTED("Not implemented"),
  MISSING_REQUIRED_FIELD("The field(s) %s is missing.", true),
  NO_AUTHORIZATION_HEADER_FOUND("No Authorization header found."),
  DOWNLOAD_ATTACH_UNAUTHORIZED("No permission to download attachment %s", true),
  TOO_MANY_ITEMS("Too many items returned", ERROR),
  TOO_MANY_ITEMS_REQUESTED("Too many items have been requested"),
  ACCESS_DENIED("Not allowed to access the requested resource"),
  ILLEGAL_FIELD("The field %s is invalid.", true),
  ILLEGAL_VALUE("The value provided for field %s is invalid.", true),
  PERMISSION_ERROR("Operation involves user %s who does not have permission.", true),
  USER_MUST_BE_ASSOCIATED_WITH_AT_LEAST_ONE_ROLE(
      "The user %s must be associated with at least one role", true),
  // User account not activated
  PASSWORD_RESET_ERROR_001("Password reset is not available for your account"),
  // User account expired
  PASSWORD_RESET_ERROR_002("Password reset is not available for your account"),
  // User org is private
  PASSWORD_RESET_ERROR_003("Password reset is not available for your account"),
  // in case of multiple accounts for the email, if none of the accounts can have the reset link
  PASSWORD_RESET_ERROR_004("Password reset is not available for your account"),
  INVALID_FILE_NAME("File names cannot contain the following characters: %s", true),
  SAML_ERROR("Error while generating saml response for user %s", true),
  ACCOUNT_MIGRATION_DUPLICATED("Duplicated migration account for the same user"),
  FILE_UPLOAD_ISSUE("Internal Server Error", ERROR),
  INVALID_FILE_TYPE("We only support file types: %s", true),
  EMAIL_QUEUING_ISSUE("The email could not be queued", ERROR),
  CANNOT_CONTAIN_NON_ASCII_CHARACTERS("Should not contain any non-ASCII characters"),
  INVALID_OLD_PASSWORD_ENTRY("Does not match old entry"),
  MUST_DIFF_FROM_PROD_ACCOUNT("Must be different from production account entry"),
  MUST_DIFF_FROM_TEST_ACCOUNT("Must be different from test account entry"),
  CHANGE_LOG_LEVEL_ERROR("The container log level could not be changed"),
  AUTHENTICATION_INTERNAL_ERROR("Authentication error", ERROR),
  PORTAL_DEACTIVATED("User is not authorized to access the portal"),
  ALLOWED_RESOURCE_COUNT_REACHED("The maximum number of %s allowed is %d", true);

  private String message;
  private boolean useArgs;
  private ExceptionLogLevel logLevel;

  MessageKey(String message) {
    this(message, DEBUG);
  }

  MessageKey(String message, ExceptionLogLevel logLevel) {
    this(message, false, logLevel);
  }

  MessageKey(String message, boolean useArgs) {
    this(message, useArgs, DEBUG);
  }

  MessageKey(String message, boolean useArgs, ExceptionLogLevel logLevel) {
    this.message = message;
    this.useArgs = useArgs;
    this.logLevel = logLevel;
  }

  public boolean isUserArgs() {
    return useArgs;
  }

  public ExceptionLogLevel getLogLevel() {
    return this.logLevel;
  }

  protected String getRawMessage() {
    return message;
  }

  public String getMessage() {
    if (useArgs) {
      throw new IllegalArgumentException(
          "The message has args so getMessage(Object... args) must be called.");
    }
    return message;
  }

  public String getMessage(Object... args) {
    if (!useArgs) {
      return message;
    }

    if (args == null || args.length == 0) {
      throw new IllegalArgumentException("The args must not be null.");
    }

    return String.format(message, args);
  }
}
