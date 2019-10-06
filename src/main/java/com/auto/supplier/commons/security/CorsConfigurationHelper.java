
package com.auto.supplier.commons.security;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

public class CorsConfigurationHelper {
  public static CorsConfigurationSource createCorsConfiguration(CorsProperty corsProperty) {
    UrlBasedCorsConfigurationSource result = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(corsProperty.isCredentialsAllowed());

    for (String allowedOrigin : corsProperty.getAllowedOrigins()) {
      config.addAllowedOrigin(allowedOrigin);
    }

    for (String allowedHeader : corsProperty.getAllowedHeaders()) {
      config.addAllowedHeader(allowedHeader);
    }

    for (String allowedMethod : corsProperty.getAllowedMethods()) {
      config.addAllowedMethod(allowedMethod);
    }
    result.registerCorsConfiguration("/**", config);

    return result;
  }
}
