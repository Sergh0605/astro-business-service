package com.spuzakov.astro.astrobusinessservice.service.step;

import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import com.spuzakov.astro.astrobusinessservice.service.TelegramBotMessageSendService;
import com.spuzakov.astro.astrobusinessservice.service.UserService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
public class BirthTimeStepService implements StepService {

  private static final String MESSAGE = "Время рождения: %s.\n Теперь введите город рождения";
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME;
  private static final String ERROR_MESSAGE = "Неверный формат времени.\n Пожалуйста, введите время в формате чч:мм или выберите из календаря";

  private final TelegramBotMessageSendService telegramBotMessageSendService;
  private final UserService userService;


  @Override
  public UserStepEnum getSupportedStep() {
    return UserStepEnum.WAITING_FOR_BIRTH_TIME;
  }

  @Override
  @Transactional
  public void processMessage(Long chatId, String text) {
    LocalTime birthTime;
    try {
      birthTime = LocalTime.parse(text, DATE_TIME_FORMATTER);
    } catch (Exception e) {
      log.warn("Wrong time format: %s".formatted(e.getMessage()));
      telegramBotMessageSendService.sendMessage(chatId, ERROR_MESSAGE);
      return;
    }

    userService.setUserStep(chatId, UserStepEnum.WAITING_FOR_CITY);
    userService.setCurrentOrderBirthTime(chatId, birthTime);
    telegramBotMessageSendService.sendMessage(chatId, MESSAGE.formatted(birthTime.format(DATE_TIME_FORMATTER)));
  }
}
