package com.auto.supplier.controllers;

import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.mappers.PartMapper;
import com.auto.supplier.mappers.VariantMapper;
import com.auto.supplier.models.Part;
import com.auto.supplier.models.Variant;
import com.auto.supplier.services.PartService;
import com.auto.supplier.services.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;

@RestController
@LoggingProfiler
@RequiredArgsConstructor
@RequestMapping("/v1/variants")
public class VariantController {

  private final VariantService variantService;
  private final VariantMapper variantMapper;
  private final PartService partService;
  private final PartMapper partMapper;

  @GetMapping(value = "/{id}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Variant getVariantById(@PathVariable(value = "id") UUID id) {
    return variantMapper.toPojo(variantService.getVariantById(id));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Variant getVariantByCode(@RequestParam(value = "code") String code) {
    return variantMapper.toPojo(variantService.getVariantByCode(code));
  }

  @GetMapping(params = "modelId", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Variant> getAllVariantsByModel(@RequestParam(value = "modelId") UUID modelId) {
    return variantService.getAllVariantsByModel(modelId).stream()
        .map(variantMapper::toPojo)
        .collect(Collectors.toList());
  }

  @PutMapping(value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
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

  @PostMapping(value = "/{variantId}/parts",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.CREATED)
  public Part createPart(@PathVariable("variantId") UUID variantId,
                               @RequestBody @Valid Part part) {
    return partMapper.toPojo(partService.createPart(variantId, part));
  }
}
