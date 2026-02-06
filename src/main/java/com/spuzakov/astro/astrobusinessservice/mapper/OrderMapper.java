package com.spuzakov.astro.astrobusinessservice.mapper;

import com.spuzakov.astro.astrobusinessservice.model.Order;
import com.spuzakov.astro.astrobusinessservice.persistence.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {NatalChartMapper.class, CityMapper.class}
)
public interface OrderMapper extends ObjectMapper<OrderEntity, Order> {
  @Override
  @Mapping(target = "user", ignore = true)
  OrderEntity mapToEntity(Order order);


  @Override
  Order mapToDto(OrderEntity source);
}
