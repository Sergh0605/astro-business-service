package com.spuzakov.astro.astrobusinessservice.mapper;

import com.spuzakov.astro.astrobusinessservice.model.City;
import com.spuzakov.astro.astrobusinessservice.persistence.entity.GeoCacheEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface GeoCacheMapper {
  @Mapping(source = "displayName", target = "name")
  @Mapping(source = "lat", target = "latitude")
  @Mapping(source = "lon", target = "longitude")
  @Mapping(source = "timezone", target = "timezone")
  City mapToDto(GeoCacheEntity source);


}
