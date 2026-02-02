package com.spuzakov.astro.astrobusinessservice.client.geonames;

import com.spuzakov.astro.astrobusinessservice.client.geonames.dto.GeoNamesChildrenResponse;
import com.spuzakov.astro.astrobusinessservice.client.geonames.dto.GeoNamesTimezoneResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Sergey Puzakov {@literal <spuzakov@fil-it.ru>}
 */

@FeignClient(
    name = "geoNamesClient",
    url = "${app.geonames.base-url}"
)
public interface GeoNamesClient {
  @GetMapping("/childrenJSON")
  GeoNamesChildrenResponse getChildren(
      @RequestParam("geonameId") Long geonameId,
      @RequestParam("lang") String lang,
      @RequestParam("maxRows") Integer maxRows,
      @RequestParam("username") String username
  );

  @GetMapping("/timezoneJSON")
  GeoNamesTimezoneResponse getTimezone(
      @RequestParam("lat") Double lat,
      @RequestParam("lng") Double lng,
      @RequestParam("username") String username
  );
}
