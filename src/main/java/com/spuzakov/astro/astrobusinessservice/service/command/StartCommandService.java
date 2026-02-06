package com.spuzakov.astro.astrobusinessservice.service.command;

import com.spuzakov.astro.astrobusinessservice.enums.TelegramCommandEnum;
import com.spuzakov.astro.astrobusinessservice.enums.UserStepTrigger;
import com.spuzakov.astro.astrobusinessservice.service.TelegramBotMessageSendService;
import com.spuzakov.astro.astrobusinessservice.service.UserService;
import com.spuzakov.astro.astrobusinessservice.service.statemachine.UserStepStateMachineService;
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
  private final UserStepStateMachineService userStepStateMachineService;

  @Override
  public TelegramCommandEnum getSupportedCommand() {
    return TelegramCommandEnum.START;
  }

  @Override
  public void processCommand(long chatId) {
    if (!userService.isUserExist(chatId)) {
      userService.createUser(chatId);
    } else {
      var currentStep = userService.getUserStep(chatId);
      var nextStep = userStepStateMachineService.fire(currentStep, UserStepTrigger.TO_START);
      userService.setUserStep(chatId, nextStep);
    }
    telegramBotMessageSendService.sendMessage(chatId, MESSAGE);
  }
}
