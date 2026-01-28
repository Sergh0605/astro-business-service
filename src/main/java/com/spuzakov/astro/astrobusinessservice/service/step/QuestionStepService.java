package com.spuzakov.astro.astrobusinessservice.service.step;

import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import com.spuzakov.astro.astrobusinessservice.service.TelegramBotMessageSendService;
import com.spuzakov.astro.astrobusinessservice.service.UserService;
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
  private final UserService userService;


  @Override
  public UserStepEnum getSupportedStep() {
    return UserStepEnum.WAITING_FOR_QUESTION;
  }

  @Override
  @Transactional
  public void processMessage(Long chatId, String text) {
    log.info("User {} send question: {}", chatId, text);
    userService.setUserStep(chatId, UserStepEnum.WAITING_FOR_QUESTION);
    telegramBotMessageSendService.sendMessage(chatId, MESSAGE);
  }
}
