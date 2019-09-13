package com.auto.supplier.commons.auditing;

import org.springframework.stereotype.Component;
import java.time.ZonedDateTime;

@Component
public class CurrentTimeDateTimeService implements DateTimeService {
  public CurrentTimeDateTimeService() {
  }

  public ZonedDateTime getCurrentDateAndTime() {
    return ZonedDateTime.now();
  }
}
