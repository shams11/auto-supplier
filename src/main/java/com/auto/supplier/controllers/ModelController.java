package com.auto.supplier.controllers;

import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.mappers.ModelMapper;
import com.auto.supplier.models.Model;
import com.auto.supplier.services.ModelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@RestController
@LoggingProfiler
@RequestMapping("/v1/models")
public class ModelController {

  private final ModelService modelService;
  private final ModelMapper modelmapper;

  public ModelController(ModelService modelService, ModelMapper modelmapper) {
    this.modelService = modelService;
    this.modelmapper = modelmapper;
  }

  @GetMapping(value = "{id}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Model getModelById(@PathVariable(value = "id") UUID id) {
    return modelmapper.toPojo(modelService.getModelById(id));
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
}
