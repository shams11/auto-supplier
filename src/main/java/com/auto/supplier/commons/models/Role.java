package com.auto.supplier.commons.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString(callSuper = true, exclude = { "description", "permissions" })
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
public class Role {

  private String id;

  private String uniqueName;

  private String description;

  @Builder.Default
  @JsonIgnore
  private Set<Permission> permissions = new HashSet<>();
}
