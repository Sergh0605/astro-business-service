package com.spuzakov.astro.astrobusinessservice.service.command;

import com.spuzakov.astro.astrobusinessservice.enums.TelegramCommandEnum;
import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import com.spuzakov.astro.astrobusinessservice.service.TelegramBotMessageSendService;
import com.spuzakov.astro.astrobusinessservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Service
@RequiredArgsConstructor
public class HelpCommandService implements CommandService {

  private static final String MESSAGE =
      """
          📖 Помощь по использованию бота

          1. Используйте /newchart для создания натальной карты
          2. Укажите дату, время и место рождения
          3. Оплатите заказ
          4. Получите готовую карту с интерпретацией

          По вопросам: @support""";
  private final TelegramBotMessageSendService telegramBotMessageSendService;
  private final UserService userService;

  @Override
  public TelegramCommandEnum getSupportedCommand() {
    return TelegramCommandEnum.HELP;
  }

  @Override
  public void processCommand(long chatId) {
    userService.setUserStep(chatId, UserStepEnum.START);
    telegramBotMessageSendService.sendMessage(chatId, MESSAGE);
  }
}
