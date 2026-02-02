package com.spuzakov.astro.astrobusinessservice.service;

import com.spuzakov.astro.astrobusinessservice.enums.OrderStatusEnum;
import com.spuzakov.astro.astrobusinessservice.enums.UserStepEnum;
import com.spuzakov.astro.astrobusinessservice.mapper.OrderMapper;
import com.spuzakov.astro.astrobusinessservice.mapper.UserMapper;
import com.spuzakov.astro.astrobusinessservice.model.City;
import com.spuzakov.astro.astrobusinessservice.model.OrderNested;
import com.spuzakov.astro.astrobusinessservice.model.User;
import com.spuzakov.astro.astrobusinessservice.persistence.entity.UserEntity;
import com.spuzakov.astro.astrobusinessservice.persistence.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final OrderMapper orderMapper;

  public User getUserByTelegramId(long telegramId) {
    var userEntity = getUserEntity(telegramId);
    return userMapper.mapToDto(userEntity);
  }

  public void setUserStep(long telegramId, UserStepEnum step) {
    var userEntity = getUserEntity(telegramId);
    userEntity.setStep(step);
    userRepository.save(userEntity);
  }

  public void createUser(long telegramId) {
    var userEntity = new UserEntity();
    userEntity.setTelegramId(telegramId);
    userEntity.setStep(UserStepEnum.START);
    userEntity.setLastInteractionAt(OffsetDateTime.now());
    userRepository.save(userEntity);
  }

  public UserStepEnum getUserStep(long telegramId) {
    var userEntity = getUserEntity(telegramId);
    return userEntity.getStep();
  }

  public boolean isUserExist(long telegramId) {
    return userRepository.findByTelegramId(telegramId)
        .map(userEntity -> {
          userEntity.setLastInteractionAt(OffsetDateTime.now());
          return userEntity;})
        .isPresent();
  }

  public void addOrderAndSetCurrent(long telegramId, OrderNested orderNested) {
    var userEntity = getUserEntity(telegramId);
    var orderEntity = orderMapper.mapToEntity(orderNested);
    userEntity.addOrder(orderEntity);
    userEntity.setCurrentOrder(orderEntity);
    userRepository.save(userEntity);
  }

  public OrderNested getCurrentOrder(long telegramId) {
    var userEntity = getUserEntity(telegramId);
    return orderMapper.mapToDto(userEntity.getCurrentOrder());
  }

  public void setCurrentOrderBirthDate(long telegramId, LocalDate date) {
    var userEntity = getUserEntity(telegramId);
    var orderEntity = userEntity.getCurrentOrder();
    orderEntity.setBirthDate(date);
    userRepository.save(userEntity);
  }

  public void setCurrentOrderBirthTime(long telegramId, LocalTime time) {
    var userEntity = getUserEntity(telegramId);
    var orderEntity = userEntity.getCurrentOrder();
    orderEntity.setBirthTime(time);
    userRepository.save(userEntity);
  }

  public void setCurrentOrderBirthCity(long telegramId, City place) {
    var userEntity = getUserEntity(telegramId);
    var orderEntity = userEntity.getCurrentOrder();
    orderEntity.setCityFullName(place.getName());
    orderEntity.setCityLat(place.getLatitude());
    orderEntity.setCityLon(place.getLongitude());
    orderEntity.setCityTimezone(place.getTimezone());
    userRepository.save(userEntity);
  }

  public void setCurrentOrderStatus(long telegramId, OrderStatusEnum status) {
    var userEntity = getUserEntity(telegramId);
    var orderEntity = userEntity.getCurrentOrder();
    orderEntity.setStatus(status);
    userRepository.save(userEntity);
  }

  private UserEntity getUserEntity(long telegramId) {
    return userRepository.findByTelegramId(telegramId)
        .map(userEntity -> {
          userEntity.setLastInteractionAt(OffsetDateTime.now());
          return userEntity;})
        .orElseThrow(() -> new RuntimeException("User not found"));
  }

}
