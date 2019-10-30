package com.auto.supplier.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailProperty {
  private String username;
  private String fromAddress;
  private String createUserSubject;
  private String partProductionSubject;
}
