package com.spuzakov.astro.astrobusinessservice.model;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class NatalChart {
  private UUID id;

  /**
   * JSON с данными построенной натальной карты (положение планет, дома и т.д.)
   */
  private String chartData;
  private String interpretation;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
}
