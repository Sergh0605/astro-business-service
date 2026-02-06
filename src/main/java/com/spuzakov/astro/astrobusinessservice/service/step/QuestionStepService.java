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
public class QuestionStepService implements StepService {

  private static final String MESSAGE = "Отличный вопрос. Сейчас подумаю и отвечу.";

  private final TelegramBotMessageSendService telegramBotMessageSendService;


  @Override
  public UserStepEnum getSupportedStep() {
    return UserStepEnum.WAITING_FOR_QUESTION;
  }

  @Override
  @Transactional
  public StepProcessingResult processMessage(Long chatId, String text) {
    log.info("User {} send question: {}", chatId, text);
    telegramBotMessageSendService.sendMessage(chatId, MESSAGE);
    return StepProcessingResult.success(UserStepTrigger.NEXT);
  }
}
