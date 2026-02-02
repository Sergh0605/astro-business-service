package com.spuzakov.astro.astrobusinessservice.service.step;

import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import com.spuzakov.astro.astrobusinessservice.service.TelegramBotMessageSendService;
import com.spuzakov.astro.astrobusinessservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Service
@RequiredArgsConstructor
public class EmailStepService implements StepService {

  private static final String MESSAGE = "Перейдите по ссылке и проведите оплату. /n"
      + "После оплаты натальная карта и ее интерпретация появится в данном чате";

  private final TelegramBotMessageSendService telegramBotMessageSendService;
  private final UserService userService;


  @Override
  public UserStepEnum getSupportedStep() {
    return UserStepEnum.WAITING_FOR_PAYMENT_EMAIL;
  }

  @Override
  @Transactional
  public void processMessage(Long chatId, String text) {
    userService.setUserStep(chatId, UserStepEnum.WAITING_FOR_PAYMENT);
    telegramBotMessageSendService.sendMessage(chatId, MESSAGE);
  }
}
