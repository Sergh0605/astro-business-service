package com.spuzakov.astro.astrobusinessservice.service.command;

import com.pengrad.telegrambot.request.SendMessage;
import com.spuzakov.astro.astrobusinessservice.enums.TelegramCommandEnum;
import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import com.spuzakov.astro.astrobusinessservice.model.User;
import com.spuzakov.astro.astrobusinessservice.service.TelegramBotMessageSendService;
import com.spuzakov.astro.astrobusinessservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Service
@RequiredArgsConstructor
public class StartCommandService implements CommandService {

  private static final String MESSAGE =
      """ 
          \uD83D\uDC4B Добро пожаловать в бот для создания натальных карт! \n
          Доступные команды: \n
          /newchart - Создать новую натальную карту \n
          /myorders - Посмотреть мои заказы \n
          /help - Помощь
          """;
  private final TelegramBotMessageSendService telegramBotMessageSendService;
  private final UserService userService;

  @Override
  public TelegramCommandEnum getSupportedCommand() {
    return TelegramCommandEnum.START;
  }

  @Override
  public void processCommand(long chatId) {
    if (!userService.isUserExist(chatId)) {
      userService.createUser(chatId);
    }
    telegramBotMessageSendService.sendMessage(chatId, MESSAGE);
  }
}
