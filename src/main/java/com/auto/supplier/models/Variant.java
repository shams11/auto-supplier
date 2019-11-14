package com.auto.supplier.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonDeserialize(builder = Variant.Builder.class)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@Builder(builderClassName = "Builder", toBuilder = true)
public class Variant {

  private UUID id;

  private UUID modelId;

  private String code;

  private String name;

  private String description;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder {
  }
}
