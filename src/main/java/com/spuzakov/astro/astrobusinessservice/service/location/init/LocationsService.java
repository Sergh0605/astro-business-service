package com.spuzakov.astro.astrobusinessservice.service.location.init;

import com.spuzakov.astro.astrobusinessservice.client.geonames.dto.GeoNamesChildrenResponse.GeoName;
import com.spuzakov.astro.astrobusinessservice.client.geonames.dto.GeoNamesClient;
import com.spuzakov.astro.astrobusinessservice.client.geonames.dto.GeoNamesTimezoneResponse;
import com.spuzakov.astro.astrobusinessservice.mapper.CityMapper;
import com.spuzakov.astro.astrobusinessservice.mapper.CountryMapper;
import com.spuzakov.astro.astrobusinessservice.mapper.RegionMapper;
import com.spuzakov.astro.astrobusinessservice.model.Country;
import com.spuzakov.astro.astrobusinessservice.model.Region;
import com.spuzakov.astro.astrobusinessservice.persistence.entity.CityEntity;
import com.spuzakov.astro.astrobusinessservice.persistence.entity.CountryEntity;
import com.spuzakov.astro.astrobusinessservice.persistence.entity.RegionEntity;
import com.spuzakov.astro.astrobusinessservice.persistence.repository.CityRepository;
import com.spuzakov.astro.astrobusinessservice.persistence.repository.CountryRepository;
import com.spuzakov.astro.astrobusinessservice.persistence.repository.RegionRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationsService {

  private final CountryRepository countryRepository;
  private final RegionRepository regionRepository;
  private final CityRepository cityRepository;
  private final GeoNamesClient geoNamesClient;
  private final RegionMapper regionMapper;
  private final CityMapper cityMapper;
  private final CountryMapper countryMapper;
  private final EntityManager entityManager;

  @Value("${app.geonames.username}")
  private String username;

  @Transactional(readOnly = true)
  public List<Country> getAllUnInitializedCountries() {
    return countryRepository.findAllByInitializedIsFalse().stream()
        .map(countryMapper::mapToDto)
        .toList();
  }

  @Transactional(readOnly = true)
  public List<Region> getAllUnInitializedRegions(UUID countryId) {
    return regionRepository.findAllByCountryIdAndInitializedIsFalse(countryId).stream()
        .map(regionMapper::mapToDto)
        .toList();
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void initRegion(Region region) {
    var regionEntity = regionRepository.findById(region.getId()).orElse(null);
    if (regionEntity == null) {
      log.info("Region {} {} not found", region.getName(), region.getCountry().getName());
      return;
    }
    if (regionEntity.getInitialized()) {
      log.info("Region {} {} already initialized", region.getName(), region.getCountry().getName());
      return;
    }
    log.info("Loading cities for region {} {}", region.getName(), region.getCountry().getName());
    List<CityEntity> cities = getCities(regionEntity).stream()
        .map(c -> cityMapper.geoNameToEntity(c, regionEntity))
        .toList();

    // 3. Сохраняем города БАТЧЕМ вместе с регионами
    List<CityEntity> savedCities = cityRepository.saveAll(cities);
    log.info("Saved {} cities for region {} {}", savedCities.size(), region.getName(), region.getCountry().getName());
    regionEntity.setInitialized(true);
    regionRepository.save(regionEntity);
    log.info("Region {} {} initialized", region.getName(), region.getCountry().getName());
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void initCountry(Country country) {
    var countryEntity = countryRepository.findById(country.getId()).orElse(null);
    if (countryEntity == null) {
      log.info("Country {} not found", country.getName());
      return;
    }
    if (countryEntity.getInitialized()) {
      log.info("Country {} already initialized", country.getName());
      return;
    }
    log.info("Loading regions for country {}", country.getName());
    List<RegionEntity> regions = getRegions(countryEntity.getGeonameId()).stream()
        .map(r -> regionMapper.geoNameToEntity(r, countryEntity))
        .toList();
    regions.forEach(this::addTimezoneToRegion);
    regionRepository.saveAll(regions);
    log.info("Saved {} regions for country {}", regions.size(), country.getName());
    countryEntity.setInitialized(true);
    countryRepository.save(countryEntity);
    log.info("Country {} initialized", country.getName());
  }

  private List<GeoName> getRegions(Long countryGeonameId) {
    return geoNamesClient
        .getChildren(countryGeonameId, "ru", 500, username)
        .getGeonames().stream()
        .filter(g -> "ADM1".equals(g.getFcode()))
        .toList();
  }

  private void addTimezoneToRegion(RegionEntity region){
    var timezone = getTimezone(region.getLatitude(), region.getLongitude());
    region.setTimezone(timezone.getTimezoneId());
  }

  private List<GeoName> getCities(RegionEntity region) {
    var cities = geoNamesClient
        .getChildren(region.getGeonameId(), "ru", 1000, username)
        .getGeonames().stream()
        .filter(g -> g.getFcode().equals("PPL"))
        .toList();
    if (cities.isEmpty()) {
      var regionAsCity = new GeoName();
      regionAsCity.setName(region.getName());
      regionAsCity.setGeonameId(region.getGeonameId());
      return List.of(regionAsCity);
    }
    return cities;
  }

  private GeoNamesTimezoneResponse getTimezone(Double lat, Double lng) {
    return geoNamesClient.getTimezone(lat, lng, username);
  }
}
