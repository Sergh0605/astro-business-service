package com.spuzakov.astro.astrobusinessservice.service.step;

import com.spuzakov.astro.astrobusinessservice.enums.TelegramCommandEnum;
import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import com.spuzakov.astro.astrobusinessservice.enums.UserStepTrigger;
import com.spuzakov.astro.astrobusinessservice.service.TelegramBotMessageSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Service
@RequiredArgsConstructor
public class StartStepService implements StepService {

  private static final String MESSAGE = "Для расчета новой натальной карты введите %s или нажмите кнопку \"Натальная карта\""
      .formatted(TelegramCommandEnum.NEW_CHART.getCommand());
  private final TelegramBotMessageSendService telegramBotMessageSendService;

  @Override
  public UserStepEnum getSupportedStep() {
    return UserStepEnum.START;
  }

  @Override
  public StepProcessingResult processMessage(Long chatId, String text) {
    telegramBotMessageSendService.sendMessage(chatId, MESSAGE);
    return StepProcessingResult.success(UserStepTrigger.NEXT);
  }
}
