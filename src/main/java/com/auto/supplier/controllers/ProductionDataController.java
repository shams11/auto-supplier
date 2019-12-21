package com.auto.supplier.controllers;

import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.mappers.ProductionDataMapper;
import com.auto.supplier.models.ProductionData;
import com.auto.supplier.services.ProductionDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@LoggingProfiler
@RequestMapping("/v1/production-data")
public class ProductionDataController {

  private ProductionDataMapper productionDataMapper;
  private ProductionDataService productionDataService;

  public ProductionDataController(ProductionDataMapper productionDataMapper,
                                  ProductionDataService productionDataService) {
    this.productionDataMapper = productionDataMapper;
    this.productionDataService = productionDataService;
  }

  @PostMapping(
          consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
          produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )

  @ResponseStatus(HttpStatus.CREATED)
  public ProductionData createPart(@RequestBody @Valid ProductionData productionData) {
    return productionDataMapper.toPojo(productionDataService.createProductionData(productionData));
  }
}
