package com.spuzakov.astro.astrobusinessservice.model;

import java.util.UUID;
import lombok.Data;

@Data
public class City {
  private UUID id;
  private String name;
  private Double latitude;
  private Double longitude;
  private Region region;
  private String timezone;
}