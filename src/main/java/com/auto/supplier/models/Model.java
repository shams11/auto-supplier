package com.auto.supplier.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonDeserialize(builder = Model.Builder.class)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@Builder(builderClassName = "Builder", toBuilder = true)
public class Model {

  private String id;

  private String name;

  private String logoFileName;

  private byte[] logo;

  private UUID brandId;
}
