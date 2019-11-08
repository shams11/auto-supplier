package com.auto.supplier.controllers;

import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.entities.BrandEntity;
import com.auto.supplier.mappers.Brandmapper;
import com.auto.supplier.models.Brand;
import com.auto.supplier.services.BrandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
  private final Brandmapper brandmapper;

  public BrandController(BrandService brandService, Brandmapper brandmapper) {
    this.brandService = brandService;
    this.brandmapper = brandmapper;
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
  public byte[] getBrandLogoById(@PathVariable(value = "id") UUID id) {
     BrandEntity brandEntity = brandService.getLogoById(id);
     return brandEntity.getLogo();
  }
}
