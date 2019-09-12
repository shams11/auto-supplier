package com.auto.supplier.commons.utils;

public final class SensitiveString {
  private static final String OBFUSCATE_CHARS = "**";
  private static final int MAX_LENGTH = 6;
  private final char[] sensitiveString;

  public SensitiveString(String sensitiveString) {
    if (sensitiveString == null) {
      this.sensitiveString = null;
    } else {
      this.sensitiveString = sensitiveString.toCharArray();
    }

  }

  public String getSensitiveString() {
    return this.sensitiveString == null ? null : String.valueOf(this.sensitiveString);
  }

  public boolean isNullOrEmpty() {
    return this.sensitiveString == null || this.sensitiveString.length == 0;
  }

  public String toString() {
    if (this.sensitiveString == null) {
      return "";
    } else if (this.sensitiveString.length <= 1) {
      return "**";
    } else {
      StringBuilder builder = new StringBuilder();
      builder.append(this.sensitiveString[0]);
      if (this.sensitiveString.length > MAX_LENGTH) {
        builder.append(this.sensitiveString[1]);
      }

      builder.append("**");
      if (this.sensitiveString.length > MAX_LENGTH) {
        builder.append(this.sensitiveString[this.sensitiveString.length - 2]);
      }

      builder.append(this.sensitiveString[this.sensitiveString.length - 1]);
      return builder.toString();
    }
  }
}

