package com.spuzakov.astro.astrobusinessservice.service.step;

import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import com.spuzakov.astro.astrobusinessservice.service.TelegramBotMessageSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Service
@RequiredArgsConstructor
public class DoneStepService implements StepService {

  private static final String MESSAGE =
      "Лимит вопросов исчерпан. Создайте новый заказ или воспользуйтесь командами /start, /help, /myorders.";

  private final TelegramBotMessageSendService telegramBotMessageSendService;

  @Override
  public UserStepEnum getSupportedStep() {
    return UserStepEnum.DONE;
  }

  @Override
  @Transactional
  public StepProcessingResult processMessage(Long chatId, String text) {
    telegramBotMessageSendService.sendMessage(chatId, MESSAGE);
    return StepProcessingResult.noTransition();
  }
}
