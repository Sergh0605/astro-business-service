package com.spuzakov.astro.astrobusinessservice.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Telegram", description = "Колбэки от Telegram Bot API")
@RequestMapping("${app.telegram.bot.webhook-path}")
public interface TelegramCallbackController {

  /**
   * Endpoint для приема обновлений от Telegram
   * Telegram отправляет POST запросы на этот URL
   */
  @PostMapping
  void onUpdateReceived(@RequestBody String update);

  /**
   * Health check endpoint для webhook
   * Можно использовать для проверки доступности
   */
  @GetMapping
  ResponseEntity<Map<String, Object>> webhookHealthCheck();
}
