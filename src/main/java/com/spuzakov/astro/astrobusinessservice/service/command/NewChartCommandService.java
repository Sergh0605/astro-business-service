package com.spuzakov.astro.astrobusinessservice.service.command;

import com.spuzakov.astro.astrobusinessservice.enums.OrderStatusEnum;
import com.spuzakov.astro.astrobusinessservice.enums.TelegramCommandEnum;
import com.spuzakov.astro.astrobusinessservice.enums.UserStepTrigger;
import com.spuzakov.astro.astrobusinessservice.model.Order;
import com.spuzakov.astro.astrobusinessservice.service.TelegramBotMessageSendService;
import com.spuzakov.astro.astrobusinessservice.service.UserService;
import com.spuzakov.astro.astrobusinessservice.service.statemachine.UserStepStateMachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Service
@RequiredArgsConstructor
public class NewChartCommandService implements CommandService {
  private static final String MESSAGE1 = "Создаем новую натальную карту...";
  private static final String MESSAGE2 = "🌞 Введи дату рождения (формат дд.мм.гггг) или выбери её из календаря.";
  private final TelegramBotMessageSendService telegramBotMessageSendService;
  private final UserService userService;
  private final UserStepStateMachineService userStepStateMachineService;

  @Override
  public TelegramCommandEnum getSupportedCommand() {
    return TelegramCommandEnum.NEW_CHART;
  }

  @Override
  @Transactional
  public void processCommand(long chatId) {
    var currentStep = userService.getUserStep(chatId);
    var nextStep = userStepStateMachineService.fire(currentStep, UserStepTrigger.NEW_ORDER);
    userService.setUserStep(chatId, nextStep);
    userService.addOrderAndSetCurrent(chatId, getNewOrder());

    telegramBotMessageSendService.sendMessage(chatId, MESSAGE1);
    telegramBotMessageSendService.sendMessage(chatId, MESSAGE2);
  }

  private Order getNewOrder() {
    return Order.builder()
        .status(OrderStatusEnum.NEW)
        .build();
  }
}
