package com.spuzakov.astro.astrobusinessservice.mapper;

import com.spuzakov.astro.astrobusinessservice.model.NatalChartNested;
import com.spuzakov.astro.astrobusinessservice.model.OrderNested;
import com.spuzakov.astro.astrobusinessservice.persistence.entity.NatalChartEntity;
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
    uses = {OrderMapper.class}
)
public interface NatalChartMapper extends ObjectMapper<NatalChartEntity, NatalChartNested> {
  @Override
  @Mapping(target = "order", ignore = true)
  NatalChartEntity mapToEntity(NatalChartNested natalChartNested);

}
