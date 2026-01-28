package com.spuzakov.astro.astrobusinessservice.service;

import com.spuzakov.astro.astrobusinessservice.mapper.CityMapper;
import com.spuzakov.astro.astrobusinessservice.model.City;
import com.spuzakov.astro.astrobusinessservice.persistence.repository.CityRepository;
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

  private final CityRepository cityRepository;
  private final CityMapper cityMapper;

  public Optional<City> getByName(String name) {
    return cityRepository.findByName(name).map(cityMapper::mapToDto);
  }
}
