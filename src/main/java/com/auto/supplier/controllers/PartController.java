package com.auto.supplier.controllers;

import com.auto.supplier.commons.utils.LoggingProfiler;
import com.auto.supplier.mappers.PartMapper;
import com.auto.supplier.models.Part;
import com.auto.supplier.services.PartService;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@LoggingProfiler
@RequestMapping("/v1/parts")
public class PartController {

  private final PartService partService;
  private final PartMapper partMapper;

  public PartController(PartService partService, PartMapper partMapper) {
    this.partService = partService;
    this.partMapper = partMapper;
  }

  @GetMapping(value = "/{id}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Part getPartById(@PathVariable(value = "id") UUID id) {
    return partMapper.toPojo(partService.getPartById(id));
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Part getPartByCode(@RequestParam(value = "code") String code) {
    return partMapper.toPojo(partService.getPartByCode(code));
  }

  @GetMapping(params = "variantId", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Part> getAllPartsByVariant(@RequestParam(value = "variantId") UUID variantId) {
    return partService.getAllPartsByVariant(variantId).stream()
        .map(partMapper::toPojo)
        .collect(Collectors.toList());
  }

  @PutMapping(value = "/{id}",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Part update(
      @PathVariable("id") UUID id,
      @RequestBody Part part) {
    return partMapper.toPojo(partService.updatePart(id, part));
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") UUID id) {
    partService.delete(id);
  }
}
