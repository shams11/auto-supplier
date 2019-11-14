package com.auto.supplier.controllers;

import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.mappers.VariantMapper;
import com.auto.supplier.models.Variant;
import com.auto.supplier.services.VariantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@LoggingProfiler
@RequestMapping("/v1/variants")
public class VariantController {

  private final VariantService variantService;
  private final VariantMapper variantMapper;

  public VariantController(VariantService variantService, VariantMapper variantMapper) {
    this.variantService = variantService;
    this.variantMapper = variantMapper;
  }

  @GetMapping(value = "/{id}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Variant getVariantById(@PathVariable(value = "id") UUID id) {
    return variantMapper.toPojo(variantService.getVariantById(id));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Variant getVariantById(@RequestParam(value = "code") String code) {
    return variantMapper.toPojo(variantService.getVariantByCode(code));
  }

  @PutMapping(value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Variant update(
      @PathVariable("id") UUID id,
      @RequestBody Variant variant) {
    return variantMapper.toPojo(variantService.updateVariant(id, variant));
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") UUID id) {
    variantService.delete(id);
  }
}
