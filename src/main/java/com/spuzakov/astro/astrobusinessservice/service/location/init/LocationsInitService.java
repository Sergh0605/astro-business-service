package com.spuzakov.astro.astrobusinessservice.service.location.init;

import com.spuzakov.astro.astrobusinessservice.model.Country;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationsInitService {
  private final LocationsService locationsService;

  public void initAllLocations() {
    log.info("Try to init all locations");
    List<Country> countries = locationsService.getAllUnInitializedCountries();
    log.info("Found {} uninitialized countries", countries.size());
    countries.forEach(locationsService::initCountry);
    log.info("All countries initialized");

    countries.stream()
        .peek(country -> log.info("Try to init regions for country {}", country.getName()))
        .map(country -> locationsService.getAllUnInitializedRegions(country.getId()))
        .flatMap(Collection::stream)
        .forEach(locationsService::initRegion);
  }


}
