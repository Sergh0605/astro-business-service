package com.spuzakov.astro.astrobusinessservice.service.step;

import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import com.spuzakov.astro.astrobusinessservice.service.TelegramBotMessageSendService;
import com.spuzakov.astro.astrobusinessservice.service.UserService;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Service
public class StepMessageProocessingService {
  private final Map<UserStepEnum, StepService> stepServices;
  private final UserService userService;
  private final TelegramBotMessageSendService telegramBotMessageSendService;

  public StepMessageProocessingService(
      Set<StepService> stepServices,
      UserService userService,
      TelegramBotMessageSendService telegramBotMessageSendService
      ) {
    this.stepServices = stepServices.stream()
        .collect(Collectors.toMap(StepService::getSupportedStep, Function.identity()));
    this.userService = userService;
    this.telegramBotMessageSendService = telegramBotMessageSendService;
  }

  public void processMessage(Long chatId, String text) {
    try {
      var userStep = userService.getUserStep(chatId);
      var stepService = stepServices.get(userStep);
      if (stepService == null) {
        throw new IllegalArgumentException("Step service not found for step: " + userStep);
      }
      stepService.processMessage(chatId, text);
    } catch (Exception e) {
      telegramBotMessageSendService.sendErrorMessage(chatId, e.getMessage());
    }

  }

}
