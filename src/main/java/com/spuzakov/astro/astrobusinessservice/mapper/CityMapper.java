package com.spuzakov.astro.astrobusinessservice.mapper;

import com.spuzakov.astro.astrobusinessservice.client.geonames.dto.GeoNamesChildrenResponse.GeoName;
import com.spuzakov.astro.astrobusinessservice.model.City;
import com.spuzakov.astro.astrobusinessservice.persistence.entity.CityEntity;
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
    uses = {RegionMapper.class}
)
public interface CityMapper extends ObjectMapper<CityEntity, City> {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "region", source = "region")
  @Mapping(target = "geonameId", source = "source.geonameId")
  @Mapping(target = "name", source = "source.name")
  @Mapping(target = "latitude", source = "source.lat")
  @Mapping(target = "longitude", source = "source.lng")
  @Mapping(target = "timezone", ignore = true)
  CityEntity geoNameToEntity(GeoName source, RegionEntity region);

  @Override
  @Mapping(target = "geonameId", ignore = true)
  CityEntity mapToEntity(City source);

}
