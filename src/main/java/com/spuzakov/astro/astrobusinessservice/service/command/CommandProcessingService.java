package com.spuzakov.astro.astrobusinessservice.service.command;

import com.spuzakov.astro.astrobusinessservice.enums.TelegramCommandEnum;
import com.spuzakov.astro.astrobusinessservice.service.TelegramBotMessageSendService;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Service
public class CommandProcessingService {

  private final Map<TelegramCommandEnum, CommandService> commandServices;
  private final TelegramBotMessageSendService telegramBotMessageSendService;

  public CommandProcessingService(
      Set<CommandService> commandServices,
      TelegramBotMessageSendService telegramBotMessageSendService) {
    this.commandServices = commandServices.stream()
        .collect(Collectors.toMap(CommandService::getSupportedCommand, Function.identity()));
    this.telegramBotMessageSendService = telegramBotMessageSendService;
  }

  public void processCommand(String text, long chatId) {
    try {
      var command = TelegramCommandEnum.fromCommand(text);
      if (command == null) {
        throw new IllegalArgumentException("Command not supported: " + command);
      }
      var commandService = commandServices.get(command);
      if (commandService == null) {
        throw new IllegalArgumentException("Command not supported: " + command);
      }
      commandService.processCommand(chatId);
    } catch (Exception e) {
      telegramBotMessageSendService.sendErrorMessage(chatId, e.getMessage());
    }
  }

}
