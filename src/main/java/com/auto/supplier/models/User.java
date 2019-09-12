package com.auto.supplier.models;

import com.auto.supplier.commons.models.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
public class User {

  private UUID id;

  private String username;

  private String fname;

  private String lname;

  private String email;

  private int active;

  @Builder.Default
  private Set<Role> roles = new HashSet<>();
}
