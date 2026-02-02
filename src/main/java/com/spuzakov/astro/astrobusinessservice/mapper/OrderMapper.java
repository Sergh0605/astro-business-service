package com.spuzakov.astro.astrobusinessservice.mapper;

import com.spuzakov.astro.astrobusinessservice.model.OrderNested;
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
    uses = {NatalChartMapper.class}
)
public interface OrderMapper extends ObjectMapper<OrderEntity, OrderNested> {
  @Override
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "cityFullName", source = "city.name")
  @Mapping(target = "cityLat", source = "city.latitude")
  @Mapping(target = "cityLon", source = "city.longitude")
  @Mapping(target = "cityTimezone", source = "city.timezone")
  OrderEntity mapToEntity(OrderNested orderNested);


  @Override
  @Mapping(source = "cityFullName", target = "city.name")
  @Mapping(source = "cityLat", target = "city.latitude")
  @Mapping(source = "cityLon", target = "city.longitude")
  @Mapping(source = "cityTimezone", target = "city.timezone")
  OrderNested mapToDto(OrderEntity source);
}
