package com.auto.supplier.entities;

import com.auto.supplier.commons.exceptions.ServiceException;
import com.auto.supplier.commons.models.MessageKey;
import com.auto.supplier.commons.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "PRODUCTION_DATA",
        indexes = {
                @Index(name = "IDX_VARIANT_ID_PART_ID", columnList = "part_id, variant_id")
        })
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class ProductionDataEntity extends BaseEntity implements Serializable {

  @NonNull
  @Column(name = "part_id", nullable = false)
  public UUID partId;

  @NonNull
  @Column(name = "variant_id", nullable = false)
  public UUID variantId;


  @Column(name = "data")
  @Lob
  private String data;

  public void populateDataFromMap(Map<String, Object> data) {
    try {
      if (data != null) {
        this.data = JsonUtils.getJsonFromObject(data);
      }
    } catch (JsonProcessingException e) {
      throw new ServiceException.Builder(MessageKey.BAD_REQUEST)
              .detailMessage("Invalid filter field")
              .throwable(e)
              .build();
    }
  }

  public Map<String, Object> filterAsMap() {
    if (!StringUtils.isEmpty(data)) {
      try {
        return JsonUtils.getObjectFromJson(
                data, new TypeReference<Map<String, Object>>() {
                });
      } catch (IOException e) {
        throw new ServiceException.Builder(MessageKey.INTERNAL_SERVER_ERROR)
                .detailMessage("Content stored in the filter column is not of type Map")
                .throwable(e)
                .build();
      }
    }
    return Map.of();
  }
}
