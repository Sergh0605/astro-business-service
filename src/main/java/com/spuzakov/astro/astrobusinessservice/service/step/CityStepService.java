package com.spuzakov.astro.astrobusinessservice.service.step;

import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import com.spuzakov.astro.astrobusinessservice.enums.UserStepTrigger;
import com.spuzakov.astro.astrobusinessservice.service.CityService;
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
public class CityStepService implements StepService {

  private static final String MESSAGE1 = "Город: %s.\n ";

  private static final String MESSAGE2 =
      """
          Полные данные для расчета натальной карты:\s
          Дата рождения: %s
          Время рождения: %s
          Город рождения: %s

          Если все правильно, нажмите кнопку 'Подтвердить' или введите 'да'.\s
          Если есть ошибка нажмите кнопку 'Сброс' и начните заново\s
          """;

  private static final String ERROR_MESSAGE = "Город с названием %s не найден, введите другой город";

  private final TelegramBotMessageSendService telegramBotMessageSendService;
  private final UserService userService;
  private final CityService cityService;


  @Override
  public UserStepEnum getSupportedStep() {
    return UserStepEnum.WAITING_FOR_CITY;
  }

  @Override
  @Transactional
  public StepProcessingResult processMessage(Long chatId, String text) {
    var cityOptional = cityService.getByTextRequest(text);
    if (cityOptional.isEmpty()) {
      log.warn("City %s not found".formatted(text));
      telegramBotMessageSendService.sendMessage(chatId, ERROR_MESSAGE.formatted(text));
      return StepProcessingResult.failure();
    }

    var city = cityOptional.get();
    userService.setCurrentOrderBirthCity(chatId, city);

    var currentOrder = userService.getCurrentOrder(chatId);

    telegramBotMessageSendService.sendMessage(chatId, MESSAGE1.formatted(city.getFullName()));
    telegramBotMessageSendService.sendMessage(chatId,
        MESSAGE2.formatted(currentOrder.getBirthDate(), currentOrder.getBirthTime(), city.getFullName()));
    return StepProcessingResult.success(UserStepTrigger.NEXT);
  }
}
