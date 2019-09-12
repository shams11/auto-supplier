package com.auto.supplier.commons.auditing;

import org.springframework.data.auditing.DateTimeProvider;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

public class AuditingDateTimeProvider implements DateTimeProvider {
  private final DateTimeService currentDateTimeProvider;

  public AuditingDateTimeProvider(DateTimeService currentDateTimeProvider) {
    this.currentDateTimeProvider = currentDateTimeProvider;
  }

  @Override
  public Optional<TemporalAccessor> getNow() {
    return Optional.of(currentDateTimeProvider.getCurrentDateAndTime());
  }
}
