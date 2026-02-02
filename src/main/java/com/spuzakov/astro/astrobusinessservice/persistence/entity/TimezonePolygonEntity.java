package com.spuzakov.astro.astrobusinessservice.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.MultiPolygon;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Getter
@Setter
@Entity
@Table(name = "timezone_polygons", schema = "public")
public class TimezonePolygonEntity {

  @Id
  @Column(name="ogc_fid", nullable = false, unique = true)
  private Integer ogcFid;

  @Column(name = "tzid", nullable = false)
  private String tzid;

  @Column(name = "geom", nullable = false, columnDefinition = "geometry(MultiPolygon,4326)")
  private MultiPolygon geom;
}
