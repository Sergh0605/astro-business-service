package com.spuzakov.astro.astrobusinessservice.service.step;

import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import com.spuzakov.astro.astrobusinessservice.enums.UserStepTrigger;
import com.spuzakov.astro.astrobusinessservice.service.TelegramBotMessageSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Service
@RequiredArgsConstructor
public class PaymentStepService implements StepService {

  private static final String MESSAGE = "Данный заказ еще не оплачен. Произведите оплату по ссылке выше. ";

  private final TelegramBotMessageSendService telegramBotMessageSendService;


  @Override
  public UserStepEnum getSupportedStep() {
    return UserStepEnum.WAITING_FOR_PAYMENT;
  }

  @Override
  @Transactional
  public StepProcessingResult processMessage(Long chatId, String text) {
    telegramBotMessageSendService.sendMessage(chatId, MESSAGE);
    return StepProcessingResult.success(UserStepTrigger.NEXT);
  }
}
