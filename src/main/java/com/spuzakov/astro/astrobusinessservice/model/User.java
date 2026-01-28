package com.spuzakov.astro.astrobusinessservice.model;

import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Data;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Data
public class User {
  private UUID id;
  private long telegramId;
  private String username;
  private OffsetDateTime registeredAt;
  private OffsetDateTime lastInteractionAt;
  private UserStepEnum step;
  private OrderNested currentOrder;
  private List<OrderNested> orders;
}
