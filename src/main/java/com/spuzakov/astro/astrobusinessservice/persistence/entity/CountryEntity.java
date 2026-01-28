package com.spuzakov.astro.astrobusinessservice.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "countries")
@Getter
@Setter
public class CountryEntity {

  @Id
  @GeneratedValue(generator = "UUID")
  @UuidGenerator(style = UuidGenerator.Style.RANDOM)
  @Column(name = "id", nullable = false, updatable = false)
  private UUID id;

  @Column(name = "geoname_id", nullable = false, unique = true)
  private Long geonameId;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "initialized", nullable = false)
  private Boolean initialized;
}
