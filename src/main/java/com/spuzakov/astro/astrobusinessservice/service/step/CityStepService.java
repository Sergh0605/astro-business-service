package com.spuzakov.astro.astrobusinessservice.service.step;

import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import com.spuzakov.astro.astrobusinessservice.model.City;
import com.spuzakov.astro.astrobusinessservice.service.CityService;
import com.spuzakov.astro.astrobusinessservice.service.TelegramBotMessageSendService;
import com.spuzakov.astro.astrobusinessservice.service.UserService;
import java.time.LocalTime;
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
      "Полные данные для расчета натальной карты:\n "
          + "Дата рождения: %s\n"
          + "Время рождения: %s\n"
          + "Город рождения: %s\n"
          + "Если все правильно, нажмите кнопку 'Подтвердить' или введите 'да'. "
          + "Если есть ошибка нажмите кнопку 'Сброс' и начните заново";

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
  public void processMessage(Long chatId, String text) {
    var cityOptional = cityService.getByName(text);
    if (cityOptional.isEmpty()) {
      log.warn("City %s not found".formatted(text));
      telegramBotMessageSendService.sendMessage(chatId, ERROR_MESSAGE.formatted(text));
      return;
    }

    var city = cityOptional.get();
    userService.setUserStep(chatId, UserStepEnum.WAITING_FOR_APPROVE);
    userService.setCurrentOrderBirthCity(chatId, city);

    var currentOrder = userService.getCurrentOrder(chatId);

    telegramBotMessageSendService.sendMessage(chatId, MESSAGE1.formatted(city.getName()));
    telegramBotMessageSendService.sendMessage(chatId,
        MESSAGE2.formatted(currentOrder.getBirthDate(), currentOrder.getBirthTime(), city.getName()));
  }
}
