package com.auto.supplier.controllers;

import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.mappers.BrandMapper;
import com.auto.supplier.mappers.ModelMapper;
import com.auto.supplier.models.Brand;
import com.auto.supplier.models.Model;
import com.auto.supplier.services.BrandService;
import com.auto.supplier.services.ModelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@RestController
@LoggingProfiler
@RequestMapping("/v1/brands")
public class BrandController {

  private final BrandService brandService;
  private final BrandMapper brandmapper;
  private final ModelService modelService;
  private final ModelMapper modelmapper;

  public BrandController(BrandService brandService, BrandMapper brandmapper,
                         ModelService modelService, ModelMapper modelmapper) {
    this.brandService = brandService;
    this.brandmapper = brandmapper;
    this.modelService = modelService;
    this.modelmapper = modelmapper;
  }


  @PostMapping(
      consumes = {"multipart/form-data"},
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  @ResponseStatus(HttpStatus.CREATED)
  public Brand create(@RequestParam(value = "name") String name,
                      @RequestParam("logo") MultipartFile logo) {
    return brandmapper.toPojo(brandService.createBrand(name, logo));
  }

  @GetMapping(value = "{id}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Brand getBrandById(@PathVariable(value = "id") UUID id) {
    return brandmapper.toPojo(brandService.getLogoById(id));
  }

  @PutMapping(value = "/{id}",
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Brand update(
      @PathVariable("id") UUID id,
      @RequestParam("logo") MultipartFile logo,
      @RequestParam(value = "name") String name) {
    return brandmapper.toPojo(brandService.updateBrand(id, name, logo));
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") UUID id) {
    brandService.delete(id);
  }

  @PostMapping( value = "/{id}/models",
      consumes = {"multipart/form-data"},
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  @ResponseStatus(HttpStatus.CREATED)
  public Model createModel(@PathVariable("id") UUID id,
                           @RequestParam(value = "name") String name,
                      @RequestParam("logo") MultipartFile logo) {
    return modelmapper.toPojo(modelService.createModel(id, name, logo));
  }
}
