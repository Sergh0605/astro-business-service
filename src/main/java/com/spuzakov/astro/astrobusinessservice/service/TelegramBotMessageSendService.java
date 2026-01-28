package com.spuzakov.astro.astrobusinessservice.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Service
@RequiredArgsConstructor
public class TelegramBotMessageSendService {
  private static final String ERROR_MESSAGE_TEMPLATE = "Произошла ошибка: %s";
  private final TelegramBot bot;

  public void sendMessage(long chatId, String text) {
    bot.execute(new SendMessage(chatId, text));
  }

  public void sendErrorMessage(long chatId, String message) {
    bot.execute(new SendMessage(chatId, String.format(ERROR_MESSAGE_TEMPLATE, message)));
  }

}
