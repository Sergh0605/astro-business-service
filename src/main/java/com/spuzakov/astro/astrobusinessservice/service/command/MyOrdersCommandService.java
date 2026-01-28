package com.spuzakov.astro.astrobusinessservice.service.command;

import com.spuzakov.astro.astrobusinessservice.enums.TelegramCommandEnum;
import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import com.spuzakov.astro.astrobusinessservice.service.TelegramBotMessageSendService;
import com.spuzakov.astro.astrobusinessservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Service
@RequiredArgsConstructor
public class MyOrdersCommandService implements CommandService {
  private static final String MESSAGE = "📋 Ваши заказы:\n\nУ вас пока нет заказов.";
  private final TelegramBotMessageSendService telegramBotMessageSendService;
  private final UserService userService;

  @Override
  public TelegramCommandEnum getSupportedCommand() {
    return TelegramCommandEnum.MY_ORDERS;
  }

  @Override
  public void processCommand(long chatId) {
    userService.setUserStep(chatId, UserStepEnum.ORDERS_LIST);
    telegramBotMessageSendService.sendMessage(chatId, MESSAGE);
  }
}
