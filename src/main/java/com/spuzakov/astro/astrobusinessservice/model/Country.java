package com.spuzakov.astro.astrobusinessservice.model;

import java.util.UUID;
import lombok.Data;

@Data
public class Country {
  private UUID id;
  private String name;
}