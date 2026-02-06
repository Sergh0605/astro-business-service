package com.spuzakov.astro.astrobusinessservice.service.step;

import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import com.spuzakov.astro.astrobusinessservice.enums.UserStepTrigger;
import com.spuzakov.astro.astrobusinessservice.service.TelegramBotMessageSendService;
import com.spuzakov.astro.astrobusinessservice.service.UserService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Service
@RequiredArgsConstructor
public class BirthDateStepService implements StepService {

  private static final String MESSAGE = "Дата рождения: %s.\n Теперь введите время рождения в формате чч:мм";
  private static final String ERROR_MESSAGE = "Неверный формат даты.\n Пожалуйста, введите дату в формате дд.мм.гггг или выберите из календаря";
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

  private final TelegramBotMessageSendService telegramBotMessageSendService;
  private final UserService userService;


  @Override
  public UserStepEnum getSupportedStep() {
    return UserStepEnum.WAITING_FOR_BIRTH_DATE;
  }

  @Override
  @Transactional
  public StepProcessingResult processMessage(Long chatId, String text) {
    LocalDate birthDate;
    try {
      birthDate = LocalDate.parse(text, DATE_TIME_FORMATTER);
    } catch (Exception e) {
      telegramBotMessageSendService.sendMessage(chatId, ERROR_MESSAGE);
      return StepProcessingResult.failure();
    }
    userService.setCurrentOrderBirthDate(chatId, birthDate);
    telegramBotMessageSendService.sendMessage(chatId, MESSAGE.formatted(birthDate.format(DATE_TIME_FORMATTER)));
    return StepProcessingResult.success(UserStepTrigger.NEXT);
  }
}
