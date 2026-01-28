package com.spuzakov.astro.astrobusinessservice.service.command;

import com.spuzakov.astro.astrobusinessservice.enums.TelegramCommandEnum;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

public interface CommandService {
  TelegramCommandEnum getSupportedCommand();
  void processCommand(long chatId);
}
