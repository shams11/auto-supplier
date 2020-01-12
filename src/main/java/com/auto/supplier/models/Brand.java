package com.auto.supplier.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonDeserialize(builder = Brand.Builder.class)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@Builder(builderClassName = "Builder", toBuilder = true)
public class Brand {

  private String id;

  private String name;

  private String logoFileName;

  @JsonIgnore
  private byte[] logo;

  private Organization org;

}
