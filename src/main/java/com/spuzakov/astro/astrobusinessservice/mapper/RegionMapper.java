package com.spuzakov.astro.astrobusinessservice.mapper;

import com.spuzakov.astro.astrobusinessservice.client.geonames.dto.GeoNamesChildrenResponse.GeoName;
import com.spuzakov.astro.astrobusinessservice.model.Region;
import com.spuzakov.astro.astrobusinessservice.persistence.entity.CountryEntity;
import com.spuzakov.astro.astrobusinessservice.persistence.entity.RegionEntity;
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
    uses = {CountryMapper.class}
)
public interface RegionMapper extends ObjectMapper<RegionEntity, Region> {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "country", source = "country")
  @Mapping(target = "geonameId", source = "source.geonameId")
  @Mapping(target = "name", source = "source.name")
  @Mapping(target = "initialized", constant = "false")
  RegionEntity geoNameToEntity(GeoName source, CountryEntity country);

  @Override
  @Mapping(target = "geonameId", ignore = true)
  @Mapping(target = "initialized", ignore = true)
  RegionEntity mapToEntity(Region source);

}
