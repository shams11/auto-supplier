package com.auto.supplier.controllers;

import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.mappers.ModelMapper;
import com.auto.supplier.mappers.VariantMapper;
import com.auto.supplier.models.Model;
import com.auto.supplier.models.Variant;
import com.auto.supplier.services.ModelService;
import com.auto.supplier.services.VariantService;
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
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;

@RestController
@LoggingProfiler
@RequestMapping("/v1/models")
public class ModelController {

  private final ModelService modelService;
  private final ModelMapper modelmapper;
  private final VariantMapper variantMapper;
  private final VariantService variantService;

  public ModelController(ModelService modelService, ModelMapper modelmapper,
                         VariantMapper variantMapper, VariantService variantService) {
    this.modelService = modelService;
    this.modelmapper = modelmapper;
    this.variantMapper = variantMapper;
    this.variantService = variantService;
  }

  @GetMapping(value = "/{id}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Model getModelById(@PathVariable(value = "id") UUID id) {
    return modelmapper.toPojo(modelService.getModelById(id));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Model> getAllBrands() {
    return modelService.getAllModels().stream()
        .map(modelmapper::toPojo)
        .collect(Collectors.toList());
  }

  @GetMapping(params = "brandId", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Model> getAllModelsByBrand(@RequestParam(value = "brandId") UUID brandId) {
    return modelService.getAllModelsByBrand(brandId).stream()
        .map(modelmapper::toPojo)
        .collect(Collectors.toList());
  }

  @PutMapping(value = "/{id}",
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Model update(
      @PathVariable("id") UUID id,
      @RequestParam("logo") MultipartFile logo,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "brandId") UUID brandId) {
    return modelmapper.toPojo(modelService.updateModel(id, name, logo, brandId));
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") UUID id) {
    modelService.delete(id);
  }

  @PostMapping(value = "/{modelId}/variants",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  @ResponseStatus(HttpStatus.CREATED)
  public Variant createVariant(@PathVariable("modelId") UUID modelId,
                         @RequestBody @Valid Variant variant) {
    return variantMapper.toPojo(variantService.createVariant(modelId, variant));
  }
}
