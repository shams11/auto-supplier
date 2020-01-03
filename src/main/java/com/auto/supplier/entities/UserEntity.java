package com.auto.supplier.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SA_USER",
    indexes = {
        @Index(name = "IDX_USER_USERNAME", columnList = "username", unique = true),
        @Index(name = "IDX_USER_EMAIL", columnList = "email", unique = true)
    },
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_EMAIL_ORG_ID", columnNames = {"email", "org_id"}),
                @UniqueConstraint(name = "UK_USERNAME_ORG_ID", columnNames = {"username", "org_id"})
        })
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {}) // keep {} to only include id/version from BaseEntity
@ToString
public class UserEntity extends BaseEntity implements Serializable {

  @Column(name = "username", unique = true, length = 28, nullable = false)
  private String username;

  @Column(name = "password", length = 200, nullable = false)
  private String password;

  @Column(name = "first_name", length = 100, nullable = false)
  private String fname;

  @Column(name = "last_name", length = 100, nullable = false)
  private String lname;

  @Column(name = "email", unique = true, length = 100, nullable = false)
  private String email;

  @Column(name = "active")
  private int active;

  @ManyToOne(optional = false)
  @JoinColumn(name = "org_id", nullable = false)
  private OrgEntity org;

  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      fetch = FetchType.EAGER)
  @JoinTable(name = "USER_ROLE",
      joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
      inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")},
      uniqueConstraints = @UniqueConstraint(name = "UK_USER_ID_ROLE_ID",
          columnNames = {"USER_ID", "ROLE_ID"}))
  private Set<RoleEntity> roles = new HashSet<>();

  public UserEntity(UserEntity userEntity) {
    this.setUsername(userEntity.getUsername());
    this.setPassword(userEntity.getPassword());
    this.setEmail(userEntity.getEmail());
    this.setFname(userEntity.getFname());
    this.setLname(userEntity.getLname());
    this.setOrg(userEntity.getOrg());
    this.setRoles(userEntity.getRoles());
    this.setId(userEntity.getId());
  }

  public UserEntity() {
  }

  public void addRole(RoleEntity role) {
    if (role != null) {
      this.roles.add(role);
    }
  }

  public void removeRole(RoleEntity role) {
    if (role != null) {
      this.roles.remove(role);
    }
  }
}
