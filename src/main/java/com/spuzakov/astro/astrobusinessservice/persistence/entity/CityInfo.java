package com.spuzakov.astro.astrobusinessservice.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CityInfo {

  @Column(name = "city_full_name")
  private String fullName;

  @Column(name = "city_lat")
  private Double latitude;

  @Column(name = "city_lon")
  private Double longitude;

  @Column(name = "city_timezone")
  private String timezone;
}
