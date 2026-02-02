package com.spuzakov.astro.astrobusinessservice.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Getter
@Setter
@Entity
@Table(name = "geo_cache")
public class GeoCacheEntity {

  @Id
  @GeneratedValue(generator = "UUID")
  @UuidGenerator(style = UuidGenerator.Style.RANDOM)
  @Column(name = "id", nullable = false, updatable = false)
  private UUID id;

  @Column(name = "query_text", nullable = false)
  private String queryText;

  @Column(name = "normalized_query", nullable = false)
  private String normalizedQuery;

  @Column(name = "display_name", nullable = false)
  private String displayName;

  @Column(name = "lat", nullable = false)
  private double lat;

  @Column(name = "lon", nullable = false)
  private double lon;

  @Column(name = "timezone", nullable = false)
  private String timezone;

  @CreationTimestamp
  private OffsetDateTime createdAt;
}
