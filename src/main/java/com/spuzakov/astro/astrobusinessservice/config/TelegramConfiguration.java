package com.spuzakov.astro.astrobusinessservice.config;

import com.pengrad.telegrambot.TelegramBot;
import com.spuzakov.astro.astrobusinessservice.config.init.properties.TelegramBotProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TelegramBotProperties.class)
public class TelegramConfiguration {
  private final TelegramBotProperties telegramBotProperties;

  public TelegramConfiguration(TelegramBotProperties telegramBotProperties) {
    this.telegramBotProperties = telegramBotProperties;
  }

  @Bean
  public TelegramBot telegramBot() {
    return new TelegramBot(telegramBotProperties.token());
  }

}
