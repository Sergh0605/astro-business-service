package com.spuzakov.astro.astrobusinessservice.mapper;

import com.spuzakov.astro.astrobusinessservice.model.Country;
import com.spuzakov.astro.astrobusinessservice.persistence.entity.CountryEntity;
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
public interface CountryMapper extends ObjectMapper<CountryEntity, Country> {

  @Override
  @Mapping(target = "geonameId", ignore = true)
  @Mapping(target = "initialized", ignore = true)
  CountryEntity mapToEntity(Country source);

}
