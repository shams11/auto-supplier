package com.auto.supplier.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
public class ProductionRecord  {

  private UUID id;

  private UUID partId;

  private UUID variantId;

  private List<ProductionData> productionData;

}
