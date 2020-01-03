package com.auto.supplier.models;

import com.auto.supplier.commons.models.Role;
import com.auto.supplier.commons.utils.SensitiveString;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(exclude = "password")
@JsonDeserialize(builder = User.Builder.class)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@Builder(builderClassName = "Builder", toBuilder = true)
public class User {

  private UUID id;

  private String username;

  private String fname;

  private String lname;

  private String email;

  private Organization org;

  @JsonIgnore
  private SensitiveString password;

  @JsonIgnore
  private int active;

  private Set<Role> roles;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder {
  }
}
