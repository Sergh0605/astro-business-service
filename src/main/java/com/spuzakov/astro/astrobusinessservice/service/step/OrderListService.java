package com.spuzakov.astro.astrobusinessservice.service.step;

import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import com.spuzakov.astro.astrobusinessservice.enums.UserStepTrigger;
import com.spuzakov.astro.astrobusinessservice.service.TelegramBotMessageSendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderListService implements StepService {

  private static final String MESSAGE = "Выберите заказ из списка";

  private final TelegramBotMessageSendService telegramBotMessageSendService;


  @Override
  public UserStepEnum getSupportedStep() {
    return UserStepEnum.ORDERS_LIST;
  }

  @Override
  @Transactional
  public StepProcessingResult processMessage(Long chatId, String text) {
    telegramBotMessageSendService.sendMessage(chatId, MESSAGE);
    return StepProcessingResult.success(UserStepTrigger.NEXT);
  }
}
