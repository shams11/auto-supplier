package com.auto.supplier.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "org",
        indexes = {
                @Index(name = "IDX_ORG_NAME", columnList = "unique_name", unique = true)
        })
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {})
@ToString
public class OrgEntity extends BaseEntity implements Serializable {

  @Column(name = "unique_name", nullable = false)
  private String unique_name;

  @OneToMany(mappedBy = "org" , orphanRemoval = true, cascade = CascadeType.ALL)
  private List<UserEntity> users;

  @OneToMany(mappedBy = "org" , orphanRemoval = true, cascade = CascadeType.ALL)
  private List<BrandEntity> brands;
}
