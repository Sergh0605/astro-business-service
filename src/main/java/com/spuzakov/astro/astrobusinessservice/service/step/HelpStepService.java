package com.spuzakov.astro.astrobusinessservice.service.step;

import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import com.spuzakov.astro.astrobusinessservice.enums.UserStepTrigger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Slf4j
@Service
public class HelpStepService implements StepService {

  @Override
  public UserStepEnum getSupportedStep() {
    return UserStepEnum.HELP;
  }

  @Override
  @Transactional
  public StepProcessingResult processMessage(Long chatId, String text) {
    log.info("User {} send question: {}", chatId, text);
    return StepProcessingResult.success(UserStepTrigger.NEXT);
  }
}
