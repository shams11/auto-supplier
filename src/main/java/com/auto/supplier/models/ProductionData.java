package com.auto.supplier.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.Map;
import java.util.UUID;

@ToString
@Getter
@JsonDeserialize(builder = ProductionData.Builder.class)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
@Builder(builderClassName = "Builder", toBuilder = true)
public class ProductionData {

  private UUID id;

  private UUID partId;

  private UUID variantId;

  private Map<String, Object> data;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder {
  }
}
