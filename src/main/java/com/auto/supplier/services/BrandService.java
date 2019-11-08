package com.auto.supplier.services;

import com.auto.supplier.entities.BrandEntity;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

public interface BrandService {

  BrandEntity createBrand(String name, MultipartFile logo);

  BrandEntity getLogoById(UUID id);

  void delete(UUID id);

  BrandEntity updateBrand(UUID id, String name, MultipartFile logo);

}
