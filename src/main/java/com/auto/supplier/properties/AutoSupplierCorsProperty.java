package com.auto.supplier.properties;

import com.auto.supplier.commons.security.CorsProperty;
import org.apache.commons.lang3.StringUtils;
import java.util.Arrays;
import java.util.List;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
public class AutoSupplierCorsProperty implements CorsProperty {

  private String origins;

  @Override
  public List<String> getAllowedOrigins() {
    if (StringUtils.isBlank(origins)) {
      throw new IllegalStateException("CORS allowed origins must be configured.");
    }
    // Remove whitespace and split by comma
    List<String> result = Arrays.asList(origins.split("\\s*,\\s*"));
    log.info("Using {} CORS origin: {}", result.size(), origins);
    return result;
  }

}
