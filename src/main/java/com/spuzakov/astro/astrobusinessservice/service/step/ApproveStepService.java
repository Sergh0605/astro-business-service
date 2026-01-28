package com.spuzakov.astro.astrobusinessservice.service.step;

import com.spuzakov.astro.astrobusinessservice.enums.OrderStatusEnum;
import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import com.spuzakov.astro.astrobusinessservice.service.TelegramBotMessageSendService;
import com.spuzakov.astro.astrobusinessservice.service.UserService;
import com.spuzakov.astro.astrobusinessservice.service.command.StartCommandService;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Service
@RequiredArgsConstructor
public class ApproveStepService implements StepService {

  private static final String MESSAGE = "Данные заказа подтверждены.\n Введите ваш email для отправки чека об оплате.";
  private static final String APPROVE_MESSAGE = "ДА";
  private static final String DECLINE_MESSAGE = "НЕТ";

  private final TelegramBotMessageSendService telegramBotMessageSendService;
  private final UserService userService;
  private final StartCommandService startCommandService;


  @Override
  public UserStepEnum getSupportedStep() {
    return UserStepEnum.WAITING_FOR_APPROVE;
  }

  @Override
  @Transactional
  public void processMessage(Long chatId, String text) {
    if (text.toUpperCase(Locale.ROOT).equals(APPROVE_MESSAGE)) {
      userService.setUserStep(chatId, UserStepEnum.WAITING_FOR_PAYMENT_EMAIL);
      userService.setCurrentOrderStatus(chatId, OrderStatusEnum.WAITING_FOR_PAYMENT);
      telegramBotMessageSendService.sendMessage(chatId, MESSAGE);
    } else if (text.toUpperCase(Locale.ROOT).equals(DECLINE_MESSAGE)) {
      userService.setCurrentOrderStatus(chatId, OrderStatusEnum.CANCELLED);
      startCommandService.processCommand(chatId);
    }

  }
}
