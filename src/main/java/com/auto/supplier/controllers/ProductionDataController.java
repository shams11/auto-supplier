package com.auto.supplier.controllers;

import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.entities.ProductionRecordEntity;
import com.auto.supplier.mappers.ProductionDataMapper;
import com.auto.supplier.models.ProductionRecord;
import com.auto.supplier.services.ProductionDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@LoggingProfiler
@RequiredArgsConstructor
@RequestMapping("/v1/production-data")
public class ProductionDataController {

  private final ProductionDataMapper productionDataMapper;
  private final ProductionDataService productionDataService;

  @PostMapping(
          consumes = MediaType.APPLICATION_JSON_VALUE,
          produces = MediaType.APPLICATION_JSON_VALUE
  )

  @ResponseStatus(HttpStatus.CREATED)
  public ProductionRecord createProductionData(
      @RequestBody @Valid ProductionRecord productionRecord) {
    ProductionRecordEntity productionRecordEntity =
        productionDataMapper.toEntity(productionRecord);
    return productionDataMapper.toPojo(
        productionDataService.createProductionData(productionRecordEntity));
  }

  @GetMapping(value = "/{id}",
          produces = MediaType.APPLICATION_JSON_VALUE)
  public ProductionRecord getProductionDataById(@PathVariable(value = "id") UUID id) {
    return productionDataMapper.toPojo(productionDataService.findById(id));
  }
}
