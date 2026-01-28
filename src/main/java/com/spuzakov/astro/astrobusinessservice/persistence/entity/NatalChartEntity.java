package com.spuzakov.astro.astrobusinessservice.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "natal_charts", schema = "business_sch")
public class NatalChartEntity {

  @Id
  @GeneratedValue
  private UUID id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private OrderEntity order;

  /**
   * JSON с данными построенной натальной карты (положение планет, дома и т.д.)
   */
  @Column(name = "chart_data", columnDefinition = "jsonb", nullable = false)
  private String chartData;

  /**
   * Текстовая интерпретация или JSON с разметкой разделов.
   */
  @Column(name = "interpretation", columnDefinition = "text")
  private String interpretation;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private OffsetDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private OffsetDateTime updatedAt;
}
