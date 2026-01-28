package com.spuzakov.astro.astrobusinessservice.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Locations", description = "Справочник стран, регионов и городов")
@RequestMapping("/api/locations")
public interface LocationController {

  @Operation(summary = "Загрузить города и регионы из GeoNames")
  @PostMapping("/load-from-geo-names")
  ResponseEntity<?> loadFromGeoNames();

}
