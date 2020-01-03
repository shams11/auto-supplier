package com.auto.supplier.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@JsonDeserialize(builder = Organization.Builder.class)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@Builder(builderClassName = "Builder", toBuilder = true)
public class Organization {

  private String id;

  private String name;
}
