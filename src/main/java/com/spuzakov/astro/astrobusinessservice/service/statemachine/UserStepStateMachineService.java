package com.spuzakov.astro.astrobusinessservice.service.statemachine;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import com.spuzakov.astro.astrobusinessservice.enums.UserStepTrigger;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Service
public class UserStepStateMachineService {

  private final StateMachineConfig<UserStepEnum, UserStepTrigger> config;

  public UserStepStateMachineService() {
    this.config = new StateMachineConfig<>();

    for (var state : UserStepEnum.values()) {
      config.configure(state)
          .permit(UserStepTrigger.TO_START, UserStepEnum.START)
          .permit(UserStepTrigger.TO_HELP, UserStepEnum.HELP)
          .permit(UserStepTrigger.TO_ORDERS_LIST, UserStepEnum.ORDERS_LIST)
          .permit(UserStepTrigger.NEW_ORDER, UserStepEnum.WAITING_FOR_BIRTH_DATE);
    }

    config.configure(UserStepEnum.START)
        .permitReentry(UserStepTrigger.NEXT);

    config.configure(UserStepEnum.WAITING_FOR_BIRTH_DATE)
        .permit(UserStepTrigger.NEXT, UserStepEnum.WAITING_FOR_BIRTH_TIME);

    config.configure(UserStepEnum.WAITING_FOR_BIRTH_TIME)
        .permit(UserStepTrigger.NEXT, UserStepEnum.WAITING_FOR_CITY);

    config.configure(UserStepEnum.WAITING_FOR_CITY)
        .permit(UserStepTrigger.NEXT, UserStepEnum.WAITING_FOR_APPROVE);

    config.configure(UserStepEnum.WAITING_FOR_APPROVE)
        .permit(UserStepTrigger.APPROVE, UserStepEnum.WAITING_FOR_PAYMENT_EMAIL)
        .permit(UserStepTrigger.DECLINE, UserStepEnum.START);

    config.configure(UserStepEnum.WAITING_FOR_PAYMENT_EMAIL)
        .permit(UserStepTrigger.NEXT, UserStepEnum.WAITING_FOR_PAYMENT);

    config.configure(UserStepEnum.WAITING_FOR_PAYMENT)
        .permit(UserStepTrigger.NEXT, UserStepEnum.WAITING_FOR_QUESTION);

    config.configure(UserStepEnum.WAITING_FOR_QUESTION)
        .permitReentry(UserStepTrigger.NEXT)
        .permit(UserStepTrigger.QUESTIONS_LIMIT_REACHED, UserStepEnum.DONE);

    config.configure(UserStepEnum.HELP)
        .permitReentry(UserStepTrigger.NEXT);

    config.configure(UserStepEnum.ORDERS_LIST)
        .permitReentry(UserStepTrigger.NEXT);

    config.configure(UserStepEnum.DONE)
        .permitReentry(UserStepTrigger.NEXT);
  }

  public UserStepEnum fire(UserStepEnum currentState, UserStepTrigger trigger) {
    var stateMachine = new StateMachine<>(currentState, config);
    stateMachine.fire(trigger);
    return stateMachine.getState();
  }
}
