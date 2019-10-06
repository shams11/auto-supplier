package com.auto.supplier.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "auto-supplier")
public class AutoSupplierProperty {

  private AutoSupplierCorsProperty cors;

}
