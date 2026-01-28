package com.spuzakov.astro.astrobusinessservice.service.step;

import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

public interface StepService {
  UserStepEnum getSupportedStep();

  void processMessage(Long chatId, String text);

}
