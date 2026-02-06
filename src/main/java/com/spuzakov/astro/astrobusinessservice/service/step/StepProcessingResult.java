package com.spuzakov.astro.astrobusinessservice.service.step;

import com.spuzakov.astro.astrobusinessservice.enums.UserStepTrigger;

public record StepProcessingResult(boolean success, UserStepTrigger trigger) {

  public static StepProcessingResult success(UserStepTrigger trigger) {
    return new StepProcessingResult(true, trigger);
  }

  public static StepProcessingResult failure() {
    return new StepProcessingResult(false, null);
  }

  public static StepProcessingResult noTransition() {
    return new StepProcessingResult(true, null);
  }
}
