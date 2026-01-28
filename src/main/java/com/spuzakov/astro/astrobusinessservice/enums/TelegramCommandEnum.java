package com.spuzakov.astro.astrobusinessservice.enums;

import java.util.Arrays;
import lombok.Getter;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Getter
public enum TelegramCommandEnum {
  START("/start", "Начать"),
  HELP("/help", "Помощь"),
  NEW_CHART("/newchart", "Создать новую натальную карту"),
  MY_ORDERS("/myorders", "Посмотреть мои заказы");

  private final String command;
  private final String description;

  TelegramCommandEnum(String command, String description) {
    this.command = command;
    this.description = description;
  }

  public static TelegramCommandEnum fromCommand(String command) {
    return Arrays.stream(TelegramCommandEnum.values()).filter(commandEnum -> commandEnum.getCommand().equals(command)).findFirst().orElse(null);
  }
}
