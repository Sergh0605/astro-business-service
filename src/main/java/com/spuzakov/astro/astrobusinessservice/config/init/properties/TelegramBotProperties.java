package com.spuzakov.astro.astrobusinessservice.config.init.properties;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app.telegram.bot")
@Validated
public record TelegramBotProperties(
    @NotBlank(message = "Telegram bot username is required")
    String name,
    @NotBlank(message = "Telegram bot token is required")
    String token,
    @NotBlank(message = "Telegram webhook base URL is required")
    String webhookBaseUrl,
    @NotBlank(message = "Telegram webhook path is required")
    String webhookPath
) {

}
