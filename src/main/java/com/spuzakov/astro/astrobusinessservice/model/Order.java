package com.spuzakov.astro.astrobusinessservice.model;

import com.spuzakov.astro.astrobusinessservice.enums.OrderStatusEnum;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Order {
  private UUID id;
  private City city;
  private LocalDate birthDate;
  private LocalTime birthTime;
  private OrderStatusEnum status;
  private Instant paidAt;
  private Instant createdAt;
  private Instant updatedAt;
  private NatalChart natalChart;

}

