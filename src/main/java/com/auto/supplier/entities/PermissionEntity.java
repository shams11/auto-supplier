package com.auto.supplier.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "permission")
@Getter
@Setter
@ToString(callSuper = true, exclude = "description")
@EqualsAndHashCode(callSuper = true, of = {}) // keep {} to only include id/version from BaseEntity
public class PermissionEntity extends BaseEntity implements Serializable {

  @NonNull
  @Column(name = "unique_name", nullable = false)
  public String uniqueName;

  public String description;
}
