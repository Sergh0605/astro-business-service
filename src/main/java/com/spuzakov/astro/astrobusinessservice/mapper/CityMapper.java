package com.spuzakov.astro.astrobusinessservice.mapper;

import com.spuzakov.astro.astrobusinessservice.model.City;
import com.spuzakov.astro.astrobusinessservice.persistence.entity.CityInfo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CityMapper {

  City mapToModel(CityInfo entity);

  CityInfo toEntity(City model);

}
