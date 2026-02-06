package com.spuzakov.astro.astrobusinessservice.persistence.entity;

import com.spuzakov.astro.astrobusinessservice.enums.OrderStatusEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class OrderEntity {

  @Id
  @GeneratedValue(generator = "UUID")
  @UuidGenerator(style = UuidGenerator.Style.RANDOM)
  @Column(nullable = false, updatable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false) // связь с users
  private UserEntity user;

  @Embedded
  private CityInfo city;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  @Column(name = "birth_time")
  private LocalTime birthTime;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private OrderStatusEnum status;

  @Column(name = "paid_at")
  private Instant paidAt;

  @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  private NatalChartEntity natalChart;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  public void setNatalChart(NatalChartEntity natalChart) {
    this.natalChart = natalChart;
    if (natalChart != null) {
      natalChart.setOrder(this);
    }
  }
}
