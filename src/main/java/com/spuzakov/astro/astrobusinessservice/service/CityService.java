package com.spuzakov.astro.astrobusinessservice.service;

import com.spuzakov.astro.astrobusinessservice.client.nominatim.NominatimClient;
import com.spuzakov.astro.astrobusinessservice.mapper.GeoCacheMapper;
import com.spuzakov.astro.astrobusinessservice.model.City;
import com.spuzakov.astro.astrobusinessservice.persistence.entity.GeoCacheEntity;
import com.spuzakov.astro.astrobusinessservice.persistence.repository.GeoCacheRepository;
import com.spuzakov.astro.astrobusinessservice.persistence.repository.TimezoneRepository;
import java.util.Locale;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@Service
@RequiredArgsConstructor
@Transactional
public class CityService {

  private final GeoCacheRepository geoCacheRepository;
  private final NominatimClient nominatimClient;
  private final TimezoneRepository timezoneRepo;

  private final GeoCacheMapper geoCacheMapper;


  public Optional<City> getByTextRequest(String fullText) {
    String normalizedRequest = normalize(fullText);

    return geoCacheRepository.findByNormalizedQuery(normalizedRequest)
        .map(geoCacheMapper::mapToDto)
        .or(() -> fetchAndCache(fullText, normalizedRequest).map(geoCacheMapper::mapToDto));
  }

  private Optional<GeoCacheEntity> fetchAndCache(String raw, String norm) {

    var place = nominatimClient.searchOne(raw);

    return place.map(p -> {
      String tz = timezoneRepo.findTimezone(p.getLon(), p.getLat());

      GeoCacheEntity e = new GeoCacheEntity();
      e.setQueryText(raw);
      e.setNormalizedQuery(norm);
      e.setDisplayName(p.getDisplayName());
      e.setLat(p.getLat());
      e.setLon(p.getLon());
      e.setTimezone(tz);
      return geoCacheRepository.save(e);
    });
  }

  private String normalize(String q) {
    return q
        .trim()
        .toLowerCase(Locale.ROOT)
        .replaceAll("\\s+", " ");
  }

}
