package com.spuzakov.astro.astrobusinessservice.controller;

import com.spuzakov.astro.astrobusinessservice.controller.api.LocationController;
import com.spuzakov.astro.astrobusinessservice.service.location.init.LocationsInitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LocationControllerImpl implements LocationController {
private final LocationsInitService locationsInitService;

  @Override
  public ResponseEntity<?> loadFromGeoNames() {
    locationsInitService.initAllLocations();
    return ResponseEntity.ok().build();
  }
}
