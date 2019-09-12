package com.auto.supplier.commons.exceptions;


import com.auto.supplier.commons.models.ExceptionLogLevel;
import com.auto.supplier.commons.models.MessageKey;
import com.google.common.base.Strings;
import java.util.List;

public class ServiceException extends RuntimeException {
  private static final Integer DEFAULT_EXTERNAL_ERROR_CODE = 10000;
  private final MessageKey key;
  private final Object[] args;
  private final Integer externalErrorCode;
  private final List<String> externalErrorMessages;
  private final ExceptionLogLevel logLevel;

  private ServiceException(MessageKey key, Integer externalErrorCode, String detailMessage,
                           ExceptionLogLevel logLevel,
                           List<String> externalErrorMessages, Throwable t, Object... args) {
    super(detailMessage, t);
    this.key = key;
    this.args = args;
    this.externalErrorCode = externalErrorCode == null ?
        DEFAULT_EXTERNAL_ERROR_CODE : externalErrorCode;
    this.externalErrorMessages = externalErrorMessages;
    this.logLevel = logLevel != null ? logLevel : key.getLogLevel();
  }

  public static class Builder {
    private MessageKey key;
    private Object[] args;
    private Integer externalErrorCode;
    private List<String> externalErrorMessages;
    private String detailMessage;
    private Throwable throwable;
    private ExceptionLogLevel logLevel;

    public Builder(MessageKey key) {
      this.key = key;
    }

    public Builder logLevel(ExceptionLogLevel logLevel) {
      this.logLevel = logLevel;
      return this;
    }

    public Builder externalErrorCode(Integer externalErrorCode) {
      this.externalErrorCode = externalErrorCode;
      return this;
    }

    public Builder externalErrorMessages(List<String> externalErrorMessages) {
      this.externalErrorMessages = externalErrorMessages;
      return this;
    }

    public Builder args(Object... args) {
      this.args = args;
      return this;
    }

    public Builder detailMessage(String detailMessage) {
      this.detailMessage = detailMessage;
      return this;
    }

    public Builder throwable(Throwable throwable) {
      this.throwable = throwable;
      return this;
    }

    public ServiceException build() {
      if (key.isUserArgs() && (args == null || args.length == 0)) {
        throw new IllegalArgumentException("The specified key needs non-null args.");
      }
      return new ServiceException(key, externalErrorCode, detailMessage, logLevel,
          externalErrorMessages,
          throwable, args);
    }
  }

  public ExceptionLogLevel getLogLevel() {
    return logLevel;
  }

  public MessageKey getKey() {
    return this.key;
  }

  public Integer getExternalErrorCode() {
    return this.externalErrorCode;
  }

  public List<String> getExternalErrorMessages() {
    return this.externalErrorMessages;
  }

  public String getErrorMessage() {
    return key.getMessage(args);
  }

  @Override
  public String getMessage() {
    String result = super.getMessage();
    if (Strings.isNullOrEmpty(result)) {
      return getErrorMessage();
    }
    return result;
  }
}

