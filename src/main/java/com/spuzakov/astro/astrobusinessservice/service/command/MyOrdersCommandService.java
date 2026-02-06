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
public class MyOrdersCommandService implements CommandService {
  private static final String MESSAGE = "📋 Ваши заказы:\n\nУ вас пока нет заказов.";
  private final TelegramBotMessageSendService telegramBotMessageSendService;
  private final UserService userService;
  private final UserStepStateMachineService userStepStateMachineService;

  @Override
  public TelegramCommandEnum getSupportedCommand() {
    return TelegramCommandEnum.MY_ORDERS;
  }

  @Override
  public void processCommand(long chatId) {
    var currentStep = userService.getUserStep(chatId);
    var nextStep = userStepStateMachineService.fire(currentStep, UserStepTrigger.TO_ORDERS_LIST);
    userService.setUserStep(chatId, nextStep);
    telegramBotMessageSendService.sendMessage(chatId, MESSAGE);
  }
}
