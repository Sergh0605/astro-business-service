package com.spuzakov.astro.astrobusinessservice.model;

import lombok.Data;

@Data
public class City {
  private String fullName;
  private Double latitude;
  private Double longitude;
  private String timezone;
}