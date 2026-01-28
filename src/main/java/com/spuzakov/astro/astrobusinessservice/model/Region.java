package com.spuzakov.astro.astrobusinessservice.model;

import java.util.UUID;
import lombok.Data;

@Data
public class Region {
  private UUID id;
  private String name;
  private Country country;
}