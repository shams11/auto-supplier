package com.auto.supplier.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "MODEL",
    indexes = {
        @Index(name = "IDX_MODEL_NAME", columnList = "unique_name", unique = true)
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_MODEL_NAME", columnNames = {"unique_name"})
    })
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {}) // keep {} to only include id/version from BaseEntity
@ToString
public class ModelEntity extends BaseEntity implements Serializable {

  @NonNull
  @Column(name = "unique_name", nullable = false)
  public String uniqueName;

  @Column(
      name = "logo_file_name"
  )
  private String modelLogoFileName;

  @Column(
      nullable = false,
      name = "logo",
      columnDefinition = "blob"
  )
  @Lob
  private byte[] modelLogo;

  @Column(
      name = "brand_id",
      nullable = false
  )
  private BrandEntity brand;

  @OneToMany(mappedBy = "model", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<VariantEntity> variants;
}
