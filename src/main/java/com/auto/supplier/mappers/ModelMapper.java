package com.auto.supplier.mappers;

import com.auto.supplier.commons.mappers.ControllerMapper;
import com.auto.supplier.entities.ModelEntity;
import com.auto.supplier.models.Model;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper implements ControllerMapper<ModelEntity, Model> {

  @Override
  public ModelEntity toEntity(Model model) {
    if (model == null) {
      return null;
    }
    ModelEntity modelEntity = new ModelEntity();
    modelEntity.setUniqueName(model.getName());
    modelEntity.setModelLogoFileName(model.getLogoFileName());
    modelEntity.setModelLogo(model.getLogo());
    return modelEntity;
  }

  @Override
  public Model toPojo(ModelEntity modelEntity) {
    if (modelEntity == null) {
      return null;
    }
    return Model.builder()
        .id(modelEntity.getId().toString())
        .brandId(modelEntity.getBrand().getId())
        .name(modelEntity.getUniqueName())
        .logoFileName(modelEntity.getModelLogoFileName())
        .logo(modelEntity.getModelLogo())
        .build();
  }
}
