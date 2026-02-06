package com.spuzakov.astro.astrobusinessservice.mapper;

import com.spuzakov.astro.astrobusinessservice.model.NatalChart;
import com.spuzakov.astro.astrobusinessservice.persistence.entity.NatalChartEntity;
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
public interface NatalChartMapper extends ObjectMapper<NatalChartEntity, NatalChart> {
  @Override
  @Mapping(target = "order", ignore = true)
  NatalChartEntity mapToEntity(NatalChart natalChart);

}
