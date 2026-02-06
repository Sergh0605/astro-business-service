package com.spuzakov.astro.astrobusinessservice.service.statemachine;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import com.spuzakov.astro.astrobusinessservice.enums.OrderStatusEnum;
import com.spuzakov.astro.astrobusinessservice.enums.OrderStatusTrigger;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusStateMachineService {

  private final StateMachineConfig<OrderStatusEnum, OrderStatusTrigger> config;

  public OrderStatusStateMachineService() {
    this.config = new StateMachineConfig<>();
    for (var status : OrderStatusEnum.values()) {
      config.configure(status).permitReentry(OrderStatusTrigger.SYNC);
    }
  }

  public OrderStatusEnum fire(OrderStatusEnum currentState, OrderStatusTrigger trigger) {
    var stateMachine = new StateMachine<>(currentState, config);
    stateMachine.fire(trigger);
    return stateMachine.getState();
  }
}
