package com.auto.supplier.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "role", uniqueConstraints = {
    @UniqueConstraint(name = "UK_ROLE_UNIQUE_NAME", columnNames = {"unique_name"})
})
@Getter
@Setter
@ToString(callSuper = true, exclude = { "description" })
@EqualsAndHashCode(callSuper = true, of = {}) // keep {} to only include id/version from BaseEntity
public class RoleEntity extends  BaseEntity implements Serializable {

  @NonNull
  @Column(name = "unique_name", nullable = false)
  public String uniqueName;

  @Column(name = "description")
  public String description;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(name = "ROLE_PERMISSION",
      joinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")},
      inverseJoinColumns = {@JoinColumn(name = "PERMISSION_ID", referencedColumnName = "ID")},
      uniqueConstraints = @UniqueConstraint(name = "UK_ROLE_ID_PERMISSION_ID",
          columnNames = {"ROLE_ID", "PERMISSION_ID"}))
  private Set<PermissionEntity> permissions = new HashSet<>();
}
