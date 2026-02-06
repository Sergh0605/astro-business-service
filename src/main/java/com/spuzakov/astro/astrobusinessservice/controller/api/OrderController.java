package com.spuzakov.astro.astrobusinessservice.controller.api;

import com.spuzakov.astro.astrobusinessservice.model.NatalChart;
import com.spuzakov.astro.astrobusinessservice.model.Order;
import com.spuzakov.astro.astrobusinessservice.model.dto.AnswerDto;
import com.spuzakov.astro.astrobusinessservice.model.dto.OrderRequestDto;
import com.spuzakov.astro.astrobusinessservice.model.dto.QuestionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Order", description = "Работа с заказами и расчетом натальных карт")
@RequestMapping("/api/orders")
public interface OrderController {

  @Operation(summary = "Создать новый заказ (регистрация пользователя в системе)")
  @PostMapping
  ResponseEntity<Order> createOrder(@RequestBody OrderRequestDto request);

  @Operation(summary = "Получить заказ по ID")
  @GetMapping("/{orderId}")
  ResponseEntity<Order> getOrder(@PathVariable UUID orderId);

  @Operation(summary = "Запросить расчет натальной карты (требуется оплата)")
  @PostMapping("/{orderId}/calculate")
  ResponseEntity<NatalChart> calculate(@PathVariable UUID orderId);

  @Operation(summary = "Отправить уточняющий вопрос по заказу")
  @PostMapping("/{orderId}/questions")
  ResponseEntity<AnswerDto> askQuestion(@PathVariable UUID orderId,
      @RequestBody QuestionDto request);
}
