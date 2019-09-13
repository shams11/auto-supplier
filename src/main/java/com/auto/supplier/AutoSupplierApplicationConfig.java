package com.auto.supplier;

import com.auto.supplier.commons.auditing.AuditingDateTimeProvider;
import com.auto.supplier.commons.auditing.DateTimeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@ComponentScan
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
public class AutoSupplierApplicationConfig {

  @Bean
  DateTimeProvider dateTimeProvider(DateTimeService dateTimeService) {
    return new AuditingDateTimeProvider(dateTimeService);
  }
}
