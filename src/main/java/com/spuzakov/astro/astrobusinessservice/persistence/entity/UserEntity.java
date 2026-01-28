package com.spuzakov.astro.astrobusinessservice.persistence.entity;

import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "users", schema = "business_sch")
@Getter
@Setter
public class UserEntity {

  @Id
  @GeneratedValue(generator = "UUID")
  @UuidGenerator(style = UuidGenerator.Style.RANDOM)
  @Column(nullable = false, updatable = false)
  private UUID id;

  @Column(name = "telegram_id", nullable = false, unique = true)
  private Long telegramId;

  @Column(name = "username")
  private String username;

  @CreationTimestamp
  @Column(name = "registered_at", nullable = false, updatable = false)
  private OffsetDateTime registeredAt;

  @Column(name = "last_interaction_at", nullable = false)
  private OffsetDateTime lastInteractionAt;

  @Column(name = "step", nullable = false)
  @Enumerated(EnumType.STRING)
  private UserStepEnum step;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "current_order_id", referencedColumnName = "id")
  private OrderEntity currentOrder;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<OrderEntity> orders;

  public void addOrder(OrderEntity order) {
    if (orders == null) {
      orders = new ArrayList<>();
    }
    orders.add(order);
    order.setUser(this);
  }

  public void setCurrentOrder(OrderEntity order) {
    this.currentOrder = order;
    if (order != null) {
      order.setUser(this);
    }
  }
}
