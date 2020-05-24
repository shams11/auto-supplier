package com.auto.supplier.entities;

import com.auto.supplier.commons.exceptions.ServiceException;
import com.auto.supplier.commons.models.MessageKey;
import com.auto.supplier.commons.utils.JsonUtils;
import com.auto.supplier.models.ProductionData;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCTION_RECORD",
        indexes = {
                @Index(name = "IDX_VARIANT_ID_PART_ID", columnList = "part_id, variant_id")
        })
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class ProductionRecordEntity extends BaseEntity implements Serializable {

  @NonNull
  @Column(name = "part_id", nullable = false)
  public UUID partId;

  @NonNull
  @Column(name = "variant_id", nullable = false)
  public UUID variantId;

  @NonNull
  @Column(name = "company_name", nullable = false)
  public String companyName;

  @NonNull
  @Column(name = "user_code", nullable = false)
  public String userCode;


  @Column(name = "data")
  @Lob
  private String productionData;

  public void populateProductionData(List<ProductionData> productionData) {
    try {
      if (productionData != null) {
        this.productionData = JsonUtils.getJsonFromObject(productionData);
      }
    } catch (JsonProcessingException e) {
      throw new ServiceException.Builder(MessageKey.BAD_REQUEST)
              .detailMessage("Invalid filter field")
              .throwable(e)
              .build();
    }
  }

  public List<ProductionData> productionDataAsList() {
    if (productionData != null) {
      try {
        return JsonUtils.getListOfObjectFromJson(productionData, ProductionData.class);
      } catch (IOException e) {
        throw new ServiceException.Builder(MessageKey.BAD_REQUEST)
                .detailMessage("Content stored in the productionData " +
                    "column is not of type ProductionData")
                .throwable(e)
                .build();
      }
    }
    return List.of();
  }
}
