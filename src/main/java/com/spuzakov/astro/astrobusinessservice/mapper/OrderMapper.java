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
    uses = {CityMapper.class, NatalChartMapper.class}
)
public interface OrderMapper extends ObjectMapper<OrderEntity, OrderNested> {
  @Override
  @Mapping(target = "user", ignore = true)
  OrderEntity mapToEntity(OrderNested orderNested);

}
