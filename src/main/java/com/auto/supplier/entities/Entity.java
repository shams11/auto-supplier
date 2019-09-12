package com.auto.supplier.entities;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface Entity {

  UUID getId();

  Long getVersion();

  ZonedDateTime getTimeCreated();

  ZonedDateTime getTimeUpdated();

  UUID getCreatedByUser();

  UUID getModifiedByUser();

  String[] getDoNotUpdateFields();
}
