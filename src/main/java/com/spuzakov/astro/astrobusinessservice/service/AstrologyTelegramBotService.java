package com.spuzakov.astro.astrobusinessservice.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.MenuButton;
import com.pengrad.telegrambot.model.MenuButtonCommands;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandScopeAllPrivateChats;
import com.pengrad.telegrambot.request.SetChatMenuButton;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.request.SetWebhook;
import com.spuzakov.astro.astrobusinessservice.config.init.properties.TelegramBotProperties;
import com.spuzakov.astro.astrobusinessservice.enums.TelegramCommandEnum;
import com.spuzakov.astro.astrobusinessservice.service.command.CommandProcessingService;
import com.spuzakov.astro.astrobusinessservice.service.step.StepMessageProocessingService;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AstrologyTelegramBotService {

  private final TelegramBot bot;
  private final TelegramBotProperties properties;
  private final CommandProcessingService commandProcessingService;
  private final StepMessageProocessingService stepMessageProocessingService;

  @Value("${server.servlet.context-path}")
  private String contextPath;


  /**
   * Инициализация бота - установка webhook и команд Выполняется ОДИН РАЗ при старте приложения
   */
  @PostConstruct
  public void init() {
    try {
      log.info("Initializing Telegram webhook bot...");
      setWebHookUrl();
      setCommands();
      setGlobalMenuButton();
      log.info("Telegram bot initialization completed successfully");

    } catch (Exception e) {
      log.error("CRITICAL: Failed to initialize Telegram bot", e);
      throw new IllegalStateException(
          "Failed to initialize Telegram bot. Application cannot start. Error: " + e.getMessage(),
          e
      );
    }
  }

  public void handleTextMessage(Message message) {
    long chatId = message.chat().id();
    String text = message.text();
    log.info("📨 Message from {}: {}", chatId, text);

    if (text.startsWith("/")) {
      commandProcessingService.processCommand(text, chatId);
    } else {
      stepMessageProocessingService.processMessage(chatId, text);
    }
  }

  public void handleCallback(CallbackQuery query) {
    Long chatId = query.maybeInaccessibleMessage().chat().id();
    String data = query.data();
    log.info("🔘 Callback from {}: {}", chatId, data);
    stepMessageProocessingService.processMessage(chatId, data);
  }

  private void setWebHookUrl() {
    var fullWebhookUrl = properties.webhookBaseUrl() + contextPath + properties.webhookPath();
    log.info("Setting WebhookUrl {}", fullWebhookUrl);
    var setWebhookCommand = new SetWebhook();
    setWebhookCommand.url(fullWebhookUrl);
    var response = bot.execute(setWebhookCommand);
    if (!response.isOk()) {
      throw new IllegalStateException("Failed to set webhook");
    }
    log.info("WebhookUrl set successfully");
  }

  private void setCommands() {
    log.info("Setting commands");
    var commands =
        Arrays.stream(TelegramCommandEnum.values())
            .map(command -> new BotCommand(command.getCommand(), command.getDescription()))
            .toArray(BotCommand[]::new);
    var setCommandsCommand = new SetMyCommands(commands);
    var commandScope = new BotCommandScopeAllPrivateChats();
    setCommandsCommand.scope(commandScope);
    var response = bot.execute(setCommandsCommand);
    if (!response.isOk()) {
      throw new IllegalStateException("Failed to set commands");
    }
    log.info("Commands set successfully");
  }

  private void setGlobalMenuButton() {
    log.info("Setting global menu button");
    var menuButton = new MenuButtonCommands();
    var setMenuButtonCommand = new SetChatMenuButton();
    setMenuButtonCommand.menuButton(menuButton);
    var response = bot.execute(setMenuButtonCommand);
    if (!response.isOk()) {
      throw new IllegalStateException("Failed to set global menu button");
    }
    log.info("Global menu button set successfully");
  }
}
