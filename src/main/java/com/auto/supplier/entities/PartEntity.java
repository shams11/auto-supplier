package com.auto.supplier.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PART",
    indexes = {
        @Index(name = "IDX_PART_NAME", columnList = "unique_name", unique = true),
        @Index(name = "IDX_PART_CODE", columnList = "code", unique = true)
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_PART_NAME", columnNames = {"unique_name"}),
        @UniqueConstraint(name = "UK_PART_CODE", columnNames = {"code"})
    })
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {}) // keep {} to only include id/version from BaseEntity
@ToString
public class PartEntity extends BaseEntity implements Serializable {

  @NonNull
  @Column(name = "unique_name", nullable = false)
  public String uniqueName;

  @NonNull
  @Column(name = "code", nullable = false)
  public String code;

  @Column(name = "description")
  public String description;

  @ManyToOne(optional = false)
  @JoinColumn(name = "variant_id", nullable = false)
  private VariantEntity variant;

  // TODO : Need to add list of attributes
}
