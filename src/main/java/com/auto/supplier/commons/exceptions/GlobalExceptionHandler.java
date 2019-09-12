package com.auto.supplier.commons.exceptions;

import com.auto.supplier.commons.models.MessageKey;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {Exception.class, RuntimeException.class})
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse globalExceptionHandler(HttpServletRequest request, Exception ex) {
    log.error("Internal server error", ex);

    return ErrorResponse.builder()
        .message("Internal Server Error. Please Try again.")
        .error(ex.getMessage())
        .build();
  }

  @ExceptionHandler(value = {
      MissingServletRequestParameterException.class,
      UnsatisfiedServletRequestParameterException.class,
      MaxUploadSizeExceededException.class
  })
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleBadRequest(HttpServletRequest request, Exception ex) {
    log.debug("Bad Request", ex);

    return ErrorResponse.builder()
        .message("Bad Request")
        .error(ex.getMessage())
        .build();
  }

  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  @ResponseBody
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponse handleMethodArgumentNotValid(HttpServletRequest request,
                                                    MethodArgumentNotValidException ex) {
    log.error("Bad Request", ex);

    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    return ErrorResponse.builder()
        .message("Bad Request")
        .errors(fieldErrors.stream().map(fieldError -> {
          if (!Strings.isNullOrEmpty(fieldError.getField())) {
            return fieldError.getField() + " " + fieldError.getDefaultMessage();
          } else {
            return fieldError.getDefaultMessage();
          }
        })
            .collect(Collectors.toList()))
        .build();
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseBody
  public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException
      (MethodArgumentTypeMismatchException e) {
    Class<?> type = e.getRequiredType();

    String message;
    if (type == null) {
      message = String.format("Invalid type for parameter %s", e.getName());
    } else if (type.isEnum()) {
      message = String.format("The parameter %s must have a value among : %s",
          e.getName(),
          Joiner.on(", ").join(type.getEnumConstants()));
    } else {
      message = String.format("The parameter %s must be of type %s",
          e.getName(),
          type.getTypeName());
    }
    ErrorResponse err = ErrorResponse.builder()
        .message(message)
        .error(MessageKey.BAD_REQUEST.name())
        .build();
    return generateResponseEntity(MessageKey.BAD_REQUEST, err);
  }

  @ExceptionHandler(value = ServiceException.class)
  @ResponseBody
  public ResponseEntity<ErrorResponse> handleServiceException(ServiceException exception) {
    logServiceException(exception);
    final ErrorResponse errorResponse = ErrorResponse.builder()
        .message(exception.getErrorMessage())
        .externalErrorCode(exception.getExternalErrorCode())
        .externalErrorMessages(exception.getExternalErrorMessages())
        .error(exception.getKey().name())
        .build();


    return generateResponseEntity(exception.getKey(), errorResponse);
  }

  private void logServiceException(ServiceException exception) {
    MessageKey exceptionKey = exception.getKey();
    String defaultMessage = String.format("Got service exception with key %s.", exceptionKey);
    switch (exception.getLogLevel()) {
      case ERROR:
        log.error(defaultMessage, exception);
        break;
      case TRACE:
        log.trace(defaultMessage, exception);
        break;
      default:
        log.debug(defaultMessage, exception);
    }
  }

  private ResponseEntity<ErrorResponse> generateResponseEntity(
      final MessageKey messageKey,
      final ErrorResponse errorResponse) {
    switch (messageKey) {
      case ENTITY_NOT_FOUND:
        return addDefaultHeaderToResponse(ResponseEntity.status(HttpStatus.NOT_FOUND))
            .body(errorResponse);
      case SERVER_TO_SERVER_TOKEN_ISSUE:
      case CLIENT_TO_SERVER_TOKEN_ISSUE:
      case EXTERNAL_SERVER_CONNECTION_ISSUE:
      case CREATE_ACCOUNT_API_ISSUE:
      case S4_MIGRATE_ACCOUNT_API_ISSUE:
      case TOO_MANY_ITEMS:
      case EXTERNAL_SERVER_API_ISSUE:
      case SAML_ERROR:
      case OFFICE365_CONNECTION_ERROR:
      case FILE_UPLOAD_ISSUE:
      case EMAIL_QUEUING_ISSUE:
      case CHANGE_LOG_LEVEL_ERROR:
      case AUTHENTICATION_INTERNAL_ERROR:
      case INTERNAL_SERVER_ERROR:
        return addDefaultHeaderToResponse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR))
            .body(errorResponse);
      case MISSING_REQUIRED_FIELD:
      case INVALID_EMAIL_FORMAT:
      case BAD_REQUEST:
      case TOO_MANY_ITEMS_REQUESTED:
      case INVALID_INVITE_TOKEN_FORMAT:
      case TOKEN_INVALID:
      case PASSWORD_RESET_MOST_RECENT_ERROR:
      case CANNOT_CONTAIN_NON_ASCII_CHARACTERS:
      case INVALID_OLD_PASSWORD_ENTRY:
      case MUST_DIFF_FROM_PROD_ACCOUNT:
      case MUST_DIFF_FROM_TEST_ACCOUNT:
      case PASSWORD_RESET_ERROR_001:
      case PASSWORD_RESET_ERROR_002:
      case PASSWORD_RESET_ERROR_003:
      case PASSWORD_RESET_ERROR_004:
      case INVALID_FILE_NAME:
      case INVALID_FILE_TYPE:
      case ILLEGAL_FIELD:
      case ILLEGAL_VALUE:
      case USER_MUST_BE_ASSOCIATED_WITH_AT_LEAST_ONE_ROLE:
      case ALLOWED_RESOURCE_COUNT_REACHED:
        return addDefaultHeaderToResponse(ResponseEntity.status(HttpStatus.BAD_REQUEST))
            .body(errorResponse);
      case USER_INVALID_CREDENTIALS:
      case USER_UNAUTHORIZED:
      case TOKEN_EXPIRED:
      case ACCOUNT_LOCKED:
      case USER_NOT_INVITED:
      case NO_AUTHORIZATION_HEADER_FOUND:
      case DOWNLOAD_ATTACH_UNAUTHORIZED:
      case PERMISSION_ERROR:
      case PORTAL_DEACTIVATED:
        return addDefaultHeaderToResponse(ResponseEntity.status(HttpStatus.UNAUTHORIZED))
            .body(errorResponse);
      case ENTITY_EXISTS:
      case USER_EXISTS:
      case ACCOUNT_MIGRATION_DUPLICATED:
        return addDefaultHeaderToResponse(ResponseEntity.status(HttpStatus.CONFLICT))
            .body(errorResponse);
      case NOT_IMPLEMENTED:
        return addDefaultHeaderToResponse(ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED))
            .body(errorResponse);
      case ACCESS_DENIED:
        return addDefaultHeaderToResponse(ResponseEntity.status(HttpStatus.FORBIDDEN))
            .body(errorResponse);
      default:
        throw new IllegalStateException("Invalid status: " + messageKey);
    }
  }

  private BodyBuilder addDefaultHeaderToResponse(BodyBuilder body) {
    return body.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
  }
}
