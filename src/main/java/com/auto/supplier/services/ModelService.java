package com.auto.supplier.services;

import com.auto.supplier.entities.ModelEntity;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

public interface ModelService {

  ModelEntity createModel(UUID id, String name, MultipartFile logo);

  ModelEntity getModelById(UUID id);

  void delete(UUID id);

  ModelEntity updateModel(UUID id, String name, MultipartFile logo, UUID brandId);

  List<ModelEntity> getAllModels();

  List<ModelEntity> getAllModelsByBrand(UUID brandId);

}
