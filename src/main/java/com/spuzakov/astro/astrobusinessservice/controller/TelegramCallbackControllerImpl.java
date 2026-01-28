package com.spuzakov.astro.astrobusinessservice.controller;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.GetWebhookInfo;
import com.pengrad.telegrambot.utility.BotUtils;
import com.spuzakov.astro.astrobusinessservice.controller.api.TelegramCallbackController;
import com.spuzakov.astro.astrobusinessservice.service.AstrologyTelegramBotService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TelegramCallbackControllerImpl implements TelegramCallbackController {

  private final TelegramBot bot;
  private final AstrologyTelegramBotService astrologyTelegramBotService;

  @Override
  public void onUpdateReceived(String stringUpdate) {
    var update = BotUtils.parseUpdate(stringUpdate);
    log.debug("Webhook received update: {} from chat: {}",
        update.updateId(),
        update.message() != null ? update.message().chat().id() : "N/A");

    if (update.message() != null) {
      astrologyTelegramBotService.handleTextMessage(update.message());
    } else if (update.callbackQuery() != null) {
      astrologyTelegramBotService.handleCallback(update.callbackQuery());
    }
  }

  @Override
  public ResponseEntity<Map<String, Object>> webhookHealthCheck() {
    try {
      var response = bot.execute(new GetWebhookInfo());
      if (!response.isOk()) {
        log.error("Failed to get webhook info {}", response.description());

        Map<String, Object> error = new HashMap<>();
        error.put("healthy", false);
        error.put("error", response.description());

        return ResponseEntity.status(500).body(error);
      }
      Map<String, Object> status = new HashMap<>();
      status.put("url", response.webhookInfo().url());
      status.put("hasCustomCertificate", response.webhookInfo().hasCustomCertificate());
      status.put("pendingUpdateCount", response.webhookInfo().pendingUpdateCount());
      status.put("lastErrorDate", response.webhookInfo().lastErrorDate());
      status.put("lastErrorMessage", response.webhookInfo().lastErrorMessage());
      status.put("maxConnections", response.webhookInfo().maxConnections());
      status.put("allowedUpdates", response.webhookInfo().allowedUpdates());

      boolean isHealthy = response.webhookInfo().url() != null
          && !response.webhookInfo().url().isEmpty()
          && (response.webhookInfo().lastErrorDate() == null || response.webhookInfo().lastErrorDate() == 0);

      status.put("healthy", isHealthy);

      return ResponseEntity.ok(status);

    } catch (Exception e) {
      log.error("Failed to get webhook info", e);

      Map<String, Object> error = new HashMap<>();
      error.put("healthy", false);
      error.put("error", e.getMessage());

      return ResponseEntity.status(500).body(error);
    }
  }
}
