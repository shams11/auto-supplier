package com.auto.supplier.commons.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import java.util.ArrayList;
import java.util.List;

public interface CorsProperty {
  List<String> getAllowedOrigins();

  default List<String> getAllowedHeaders() {
    List<String> result = new ArrayList<>();
    result.add(HttpHeaders.AUTHORIZATION);
    result.add(HttpHeaders.CONTENT_TYPE);
    return result;
  }

  default List<String> getAllowedMethods() {
    List<String> result = new ArrayList<>();
    result.add(HttpMethod.GET.toString());
    result.add(HttpMethod.POST.toString());
    result.add(HttpMethod.PUT.toString());
    result.add(HttpMethod.DELETE.toString());
    result.add(HttpMethod.PATCH.toString());
    result.add(HttpMethod.OPTIONS.toString());
    return result;
  }

  default boolean isCredentialsAllowed() {
    return true;
  }
}
