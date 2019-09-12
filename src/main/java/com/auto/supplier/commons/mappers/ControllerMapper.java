package com.auto.supplier.commons.mappers;

public interface ControllerMapper<ENTITY, POJO> {

  ENTITY toEntity(POJO var1);

  POJO toPojo(ENTITY var1);
}
