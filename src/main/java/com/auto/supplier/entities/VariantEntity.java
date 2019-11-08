package com.auto.supplier.entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "VARIANT",
    indexes = {
        @Index(name = "IDX_VARIANT_NAME", columnList = "unique_name", unique = true),
        @Index(name = "IDX_VARIANT_CODE", columnList = "code", unique = true)
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_VARIANT_NAME", columnNames = {"unique_name"}),
        @UniqueConstraint(name = "UK_VARIANT_CODE", columnNames = {"code"})
    })
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {}) // keep {} to only include id/version from BaseEntity
@ToString
public class VariantEntity extends BaseEntity implements Serializable {

  @NonNull
  @Column(name = "unique_name", nullable = false)
  public String uniqueName;

  @NonNull
  @Column(name = "code", nullable = false)
  public String code;

  @Column(name = "description")
  public String description;

  @ManyToOne(optional = false)
  @JoinColumn(name = "model_id", nullable = false)
  private ModelEntity model;

  @OneToMany(mappedBy = "variant" , orphanRemoval = true, cascade = CascadeType.ALL)
  private Set<PartEntity> parts;
}
