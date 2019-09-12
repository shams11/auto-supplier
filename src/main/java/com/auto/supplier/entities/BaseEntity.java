package com.auto.supplier.entities;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"id", "version"})
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Entity {

  @Id
  @Column(length = 16)
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  private UUID id;

  @Version
  private Long version;

  @Column(nullable = false)
  @NotNull
  @CreatedDate
  private ZonedDateTime timeCreated;

  @Column(nullable = false)
  @NotNull
  @LastModifiedDate
  private ZonedDateTime timeUpdated;

  @Column(length = 16)
  @CreatedBy
  private UUID createdByUser;

  @Column(length = 16)
  @LastModifiedBy
  private UUID modifiedByUser;

  @Override
  public String[] getDoNotUpdateFields() {
    Field[] fields = BaseEntity.class.getDeclaredFields();
    return Arrays.stream(fields).map(Field::getName).toArray(String[]::new);
  }
}
