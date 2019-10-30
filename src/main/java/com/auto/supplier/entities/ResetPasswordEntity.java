package com.auto.supplier.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "RESET_PASSWORD")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = {}) // keep {} to only include id/version from BaseEntity
@ToString
public class ResetPasswordEntity extends BaseEntity implements Serializable {

  @Column(name = "email", unique = true, length = 100, nullable = false)
  private String email;

  @Column(name = "reset_token", unique = true, nullable = false)
  private String resetToken;
}
